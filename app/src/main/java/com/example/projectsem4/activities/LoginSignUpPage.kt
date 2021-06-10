package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.admin.AdminLoginPage
import com.example.projectsem4.activities.admin.AdminSignUpPage
import com.example.projectsem4.activities.parent.ParentLoginPage
import com.example.projectsem4.databinding.ActivityLoginSignUpPageBinding

class LoginSignUpPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSignUpPageBinding
    private lateinit var viewModel: FirebaseAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginSignUpPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = FirebaseAuthViewModel(this.application)

        binding.loginsignupparentsignin.setOnClickListener{
            val intent = Intent(this@LoginSignUpPage , ParentLoginPage::class.java)
            startActivity(intent)
        }

        binding.loginsignupadminsignin.setOnClickListener{
            val intent = Intent(this@LoginSignUpPage ,AdminLoginPage::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserLiveData()?.observe(this,
            { firebaseUserLiveData ->
                if (firebaseUserLiveData != null) {
                    val intent = Intent(this@LoginSignUpPage , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
    }

}