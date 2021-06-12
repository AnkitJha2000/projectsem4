package com.example.projectsem4.ViewModels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectsem4.adapters.UserAdapter
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthViewModel () : ViewModel() {
    private var application : Application? = null
    private var authUserRepository: AuthUserRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null
    private var userDataLiveData : MutableLiveData<UserAdapter>? = null

    constructor (application: Application) : this() {
        this.application = application
        this.authUserRepository = AuthUserRepository(application)
        this.userLiveData = authUserRepository!!.getUserLiveData()
        this.loggedOutLiveData = authUserRepository!!.getLoggedOutLiveData()
        this.userDataLiveData = authUserRepository!!.getUserDataLiveData()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String, password: String) {
        authUserRepository?.login(email, password)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun signup(email: String?, password: String?) {
        authUserRepository?.signup(email, password)
    }

    fun createUser(name : String , mobile : String , age : String , email : String, usertype : String)
    {
        authUserRepository?.createUser(name, mobile, age, email, usertype)
    }


    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun logOut() {
        authUserRepository?.logOut()
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    fun getUserDataLiveData() : MutableLiveData<UserAdapter>?{
        return userDataLiveData
    }

}