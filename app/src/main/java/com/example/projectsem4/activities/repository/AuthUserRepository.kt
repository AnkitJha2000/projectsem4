package com.example.projectsem4.activities.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.projectsem4.adapters.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class AuthUserRepository constructor (application: Application) {

    private var activity : Application? = application
    private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser>? = MutableLiveData<FirebaseUser>()
    private var loggedOutLiveData: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    private var userID : MutableLiveData<String>? = MutableLiveData<String>()
    private var userDataLiveData : MutableLiveData<UserAdapter>? = MutableLiveData<UserAdapter>()

    init{
        if (firebaseAuth!!.currentUser != null) {
            userLiveData!!.postValue(firebaseAuth!!.currentUser)
            loggedOutLiveData!!.postValue(false)
            userID?.postValue(firebaseAuth!!.currentUser?.uid)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun signup(email: String?, password: String?){

        if (email != null) {
            if (password != null) {
                activity?.let {
                    firebaseAuth!!.createUserWithEmailAndPassword(email , password)
                        .addOnCompleteListener(it.mainExecutor) { task ->
                            if(task.isSuccessful) {
                                userLiveData?.postValue(firebaseAuth!!.currentUser)
                                Toast.makeText(activity, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                            } else{
                                Toast.makeText(activity ,"Email already exits or Check Your Connection" , Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email:String, password: String) {

        activity?.let {
            firebaseAuth!!.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(it.mainExecutor) { task ->
                    if(task.isSuccessful) {
                        userLiveData?.postValue(firebaseAuth!!.currentUser)
                        Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show()
                        Log.d("login successful", "login successful")
                    } else {
                        Toast.makeText(activity, "Login Failed!", Toast.LENGTH_SHORT).show()
                        Log.d("login failed", "login failed")
                    }
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
                    .addOnSuccessListener { Log.d("ankit", "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w("ankit", "Error writing document", e) }
            }
        }
    }

    fun getUser(usertype: String , uid : String) {
        var user: UserAdapter? = null

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

                    userDataLiveData?.postValue(user)

                } else {
                    Log.d(TAG, "No such document")
                }
                addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
            }
        }
    }

        fun logOut() {
            firebaseAuth!!.signOut()
            loggedOutLiveData!!.postValue(true)
            userID?.postValue(null)
        }

        fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
            return userLiveData
        }

        fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
            return loggedOutLiveData
        }

        fun getUserDataLiveData() : MutableLiveData<UserAdapter>?{
            return userDataLiveData
        }

    }
