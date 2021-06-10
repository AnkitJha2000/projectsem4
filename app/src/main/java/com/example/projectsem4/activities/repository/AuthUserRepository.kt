package com.example.projectsem4.activities.repository

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class AuthUserRepository constructor (application: Application) {

    private var activity : Application? = application
    private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser>? = MutableLiveData<FirebaseUser>()
    private var loggedOutLiveData: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    private var userID : MutableLiveData<String>? = MutableLiveData<String>()

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
            "email" to email
        )

        print("/////////////////////////// the user is created in Firestore//////////////////////////////")
        getuserid()?.let {
            FirebaseFirestore.getInstance().collection(usertype).document(it).set(user).addOnSuccessListener { documentReference ->
                Log.d("Ankit is great", "DocumentSnapshot added with ID: $documentReference")
            }
                .addOnFailureListener { e ->
                    Log.w("Ankit is great", "Error adding document", e)
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

    private fun getuserid() : String? {
        return firebaseAuth!!.uid
    }

}