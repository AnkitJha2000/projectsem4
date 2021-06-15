package com.example.projectsem4.application

import android.app.Application
import android.util.Log
import com.example.projectsem4.activities.repository.AuthUserRepository

class VaccinationApplication : Application() {

    val repository by lazy{
        Log.d("Ankit", "Vaccination application : repository created")
        AuthUserRepository()
    }

}