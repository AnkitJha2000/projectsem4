package com.example.projectsem4.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.adapters.UserAdapter
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthViewModel(val repository: AuthUserRepository) : ViewModel() {

    private val authUserRepository: AuthUserRepository = repository
    private var userLiveData: MutableLiveData<FirebaseUser>? = authUserRepository.getUserLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean>? = authUserRepository.getLoggedOutLiveData()
    private var userInfoLivaData : MutableLiveData<UserAdapter>? = authUserRepository.getUserInfoLiveData()
    private var errorLiveData : MutableLiveData<String> = authUserRepository.getErrorLiveData()

    fun login(email: String, password: String) {
        repository.login(email, password)
    }

    fun signup(email: String?, password: String?) {
        repository.signup(email, password)
    }

    fun createUser(name : String , mobile : String , age : String , email : String, usertype : String)
    {
        repository.createUser(name, mobile, age, email, usertype)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun logOut() {
        repository.logOut()
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    fun getUserInfoLiveData() : MutableLiveData<UserAdapter>? {
        return userInfoLivaData
    }

    fun getUser(userType: String, firebaseuid: String) {
        repository.getUser(userType , firebaseuid)
    }

    fun getErrorLiveData() : MutableLiveData<String>
    {
        return errorLiveData
    }

}

