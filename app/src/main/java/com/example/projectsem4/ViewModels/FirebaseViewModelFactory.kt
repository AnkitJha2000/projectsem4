package com.example.projectsem4.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.activities.repository.AuthUserRepository

class FirebaseViewModelFactory(private val repository: AuthUserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FirebaseAuthViewModel(repository) as T
    }

}