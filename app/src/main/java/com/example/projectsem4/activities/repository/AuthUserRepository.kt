package com.example.projectsem4.activities.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.MultiAutoCompleteTextView
import androidx.lifecycle.MutableLiveData
import com.example.projectsem4.adapters.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AuthUserRepository : Application() {

    private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser>? = MutableLiveData<FirebaseUser>()
    private var loggedOutLiveData: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    private var userInfoLiveData : MutableLiveData<UserAdapter> = MutableLiveData<UserAdapter>()
    private var errorLiveData : MutableLiveData<String> = MutableLiveData()
    private var profileUrl : MutableLiveData<String> = MutableLiveData<String>()

    init{
        if (firebaseAuth!!.currentUser != null) {
            userLiveData?.postValue(firebaseAuth!!.currentUser)
            loggedOutLiveData?.postValue(false)
            // setProfileUrl("http://goo.gl/gEgYUd")
        }
    }

    fun signup(email: String?, password: String?){

        if (email != null) {
            if (password != null) {
                firebaseAuth!!.createUserWithEmailAndPassword(email , password)
                        .addOnCompleteListener() { task ->
                            if(task.isSuccessful) {
                                userLiveData?.postValue(firebaseAuth!!.currentUser)
                                errorLiveData.postValue(null)
                            } else{
                                errorLiveData.postValue("Email already exits or Check Your Connection")
                            }
                        }
            }
        }
    }

    fun login(email:String, password: String) {

        firebaseAuth!!.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        userLiveData?.postValue(firebaseAuth!!.currentUser)
                        Log.d("login successful", "login successful")
                        errorLiveData.postValue(null)
                    } else {
                        Log.d("login failed", "login failed")
                        errorLiveData.postValue("Login Failed")
                    }
                }
    }

    fun createUser(name: String, mobile: String, age: String, email: String , usertype : String){

        val user = hashMapOf(
            "name" to name,
            "mobile" to mobile,
            "age" to age,
            "email" to email,
            "profileUrl" to null
        )

        print("/////////////////////////// the user is created in Firestore uid : ${Firebase.auth.currentUser?.uid} //////////////////////////////")
        Firebase.auth.currentUser?.uid.let {
            if (it != null) {
                FirebaseFirestore.getInstance().collection(usertype).document(it)
                    .set(user)
                    .addOnSuccessListener { Log.d("ankit", "DocumentSnapshot successfully written!")
                    errorLiveData.postValue(null)
                        profileUrl.postValue("http://goo.gl/gEgYUd")
                    }
                    .addOnFailureListener { e -> Log.w("ankit", "Error writing document", e)
                        errorLiveData.postValue("Error writing document")
                    }
            }
        }
    }

    fun getUser(usertype: String , uid : String) {
        var user: UserAdapter

        FirebaseFirestore.getInstance().collection(usertype).document(uid).get().apply {
            addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                    val name = document.data?.get("name")
                    val age = document.data?.get("age")
                    val email = document.data?.get("email")
                    val mobile = document.data?.get("mobile")
                    val profileUrl = document.data?.get("profileUrl")

                    user = UserAdapter(name as String?, email as String?, age as String?, profileUrl as String?, usertype , mobile as String?)

                    userInfoLiveData.postValue(user)
                    errorLiveData.postValue(null)
                    setProfileUrl(profileUrl)

                } else {
                    Log.d(TAG, "No such document")
                    errorLiveData.postValue("No such documents")
                }
                addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    errorLiveData.postValue("get failed with $exception")
                }
            }
        }
    }

    fun updateUser(usertype: String , uid : String, name : String? , mobile: String? , age : String? , profileUrl : String? , email: String?) {
        val user = hashMapOf(
            "name" to name,
            "mobile" to mobile,
            "age" to age,
            "email" to email,
            "profileUrl" to profileUrl
        )
        FirebaseFirestore.getInstance().collection(usertype).document(uid).set(user)
            .addOnSuccessListener {
                Log.d(TAG, "updateUser: the user info was updated")
                errorLiveData.postValue(null)
                setProfileUrl(profileUrl)
            }
            .addOnFailureListener {
                errorLiveData.postValue("Error while updating $it")
                Log.d(TAG, "updateUser: the user info updating was not successful")
            }
    }

    fun uploadImage(filepath : Uri, uid : String , usertype: String ) : String {

        val file = FirebaseStorage.getInstance().reference.child(usertype + "_images/" + uid + ".jpg")
        return file.putFile(filepath)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    errorLiveData.postValue(null)
                    file.downloadUrl.addOnSuccessListener {
                            url->
                        profileUrl.postValue(url.toString())
                    }
                }
            }
            .addOnFailureListener{
                errorLiveData.postValue("error while uploading : $it")
            }
            .snapshot.toString()
    }


    fun logOut() {
            firebaseAuth!!.signOut()
            loggedOutLiveData?.postValue(true)
        }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
            return userLiveData
        }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
            return loggedOutLiveData
        }

    fun getUserInfoLiveData() : MutableLiveData<UserAdapter> {
            return userInfoLiveData
    }

    fun getErrorLiveData() : MutableLiveData<String> {
        return errorLiveData
    }

    fun setProfileUrl(url : String? ){
        profileUrl.postValue(url)
    }

    fun getProfileUrl() : MutableLiveData<String>{
        return profileUrl
    }

}
