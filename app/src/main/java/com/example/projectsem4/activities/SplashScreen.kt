package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.application.VaccinationApplication

class SplashScreen : AppCompatActivity() {

    private val viewModel : FirebaseAuthViewModel by viewModels {
        FirebaseViewModelFactory((application as VaccinationApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        @Suppress("Deprecated")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel.getUserLiveData()?.observe(this , { firebaseuid ->
            viewModel.getUser("parent" , firebaseuid.uid)
            Log.d("debug_profile_fragment" , firebaseuid.uid)
        })

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@SplashScreen , MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)

    }

}