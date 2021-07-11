package com.example.projectsem4.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.repository.AuthUserRepository
import java.lang.IllegalArgumentException

class FirebaseViewModelFactory(private val repository: AuthUserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FirebaseAuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FirebaseAuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong View Model")
    }

}