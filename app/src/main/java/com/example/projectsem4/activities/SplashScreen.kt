package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.repository.AuthUserRepository

class SplashScreen : AppCompatActivity() {
    private lateinit var viewModel: FirebaseAuthViewModel
    private lateinit var repository: AuthUserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        repository = AuthUserRepository()

        val viewModelFactory = FirebaseViewModelFactory(repository)

        viewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)

        @Suppress("Deprecated")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler(Looper.getMainLooper()).postDelayed({

            viewModel.getUserLiveData()?.observe(this , { firebaseuid ->
                viewModel.getUser("parent" , firebaseuid.uid)
                Log.d("debug_profile_fragment" , firebaseuid.uid)
            })

            val intent = Intent(this@SplashScreen , MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 4000)

    }

}