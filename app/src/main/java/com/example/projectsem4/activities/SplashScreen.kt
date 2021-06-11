package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel

class SplashScreen : AppCompatActivity() {
    private lateinit var viewModel: FirebaseAuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = FirebaseAuthViewModel(this.application)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@SplashScreen , MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)

    }

}