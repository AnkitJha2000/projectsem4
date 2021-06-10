package com.example.projectsem4.activities.admin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.MainActivity
import com.example.projectsem4.databinding.ActivityAdminLoginPageBinding

class AdminLoginPage : AppCompatActivity() {
    private lateinit var binding : ActivityAdminLoginPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = FirebaseAuthViewModel(this.application)

        binding.adminloginbtn.setOnClickListener{
            if(!validateEmail() || !validatePassword())
            {
                return@setOnClickListener
            }
            else
            {
                val email = binding.adminloginemail.editText?.text.toString().trim()
                val password = binding.adminloginpassword.editText?.text.toString().trim()
                viewModel.login(email , password )

                viewModel.getUserLiveData()?.observe(this,
                    { firebaseUser ->
                        if (firebaseUser != null) {
                            val intent = Intent(this@AdminLoginPage, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
        }
        binding.adminlogintosignup.setOnClickListener{
            startActivity(Intent(this@AdminLoginPage , AdminSignUpPage::class.java))
            finish()
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.adminloginemail.editText?.text.toString().trim()
        val noWhite = Regex("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        Log.d("problems","///////////////////////// $email /////////////////////////////////////////////////////////////")
        return if(email.isEmpty())
        {
            binding.adminloginemail.isErrorEnabled = true
            binding.adminloginemail.error = "Field can't be empty"
            false
        }
        else if(!email.contains("@") && !email.contains("."))
        {
            binding.adminloginemail.isErrorEnabled = true
            binding.adminloginemail.error = "Enter a valid email"
            false
        }
        else if (email.length < 6)
        {
            binding.adminloginemail.isErrorEnabled = true
            binding.adminloginemail.error = "Use at least 5 characters"
            false
        }
        else if(!noWhite.containsMatchIn(email))
        {
            binding.adminloginemail.isErrorEnabled = true
            binding.adminloginemail.error = "Enter valid email"
            false
        }
        else
        {
            binding.adminloginemail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.adminloginpassword.editText?.text.toString().trim()
        Log.d("problems","///////////////////////// $password /////////////////////////////////////////////////////////////")
        val noWhites = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        return when {
            password.isEmpty() -> {
                binding.adminloginpassword.error = "Field can't be Empty"
                false
            }
            !noWhites.containsMatchIn(password) -> {
                binding.adminloginpassword.error = "Password is too weak"
                false
            }
            else -> {
                binding.adminloginpassword.error = null
                binding.adminloginpassword.isErrorEnabled = false
                true
            }
        }
    }

}