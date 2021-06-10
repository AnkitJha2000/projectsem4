package com.example.projectsem4.activities.admin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.MainActivity
import com.example.projectsem4.databinding.ActivityAdminSignUpPageBinding

class AdminSignUpPage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminSignUpPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminSignUpPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = FirebaseAuthViewModel(this.application)
        binding.signuptologin.setOnClickListener {
            startActivity(Intent(this@AdminSignUpPage , AdminLoginPage::class.java))
            finish()
        }

        binding.signupcreatebtn.setOnClickListener{
            if(!validateName() || !validateMobile() || !validateEmail() || !validatePassword())
                return@setOnClickListener
            else
            {
                val email = binding.signupemail.editText?.text.toString()
                val mobile = binding.signupmobile.editText?.text.toString()
                val password = binding.signuppassword.editText?.text.toString()
                val name = binding.signupname.editText?.text.toString()
                viewModel.signup(email , password )
                viewModel.createUser(name , mobile , "no age " , email , "admin")

                viewModel.getUserLiveData()?.observe(this,
                    { firebaseUser ->
                        if (firebaseUser != null) {
                            val intent = Intent(this@AdminSignUpPage , MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
        }
    }

    private fun validateName(): Boolean {
        val name = binding.signupname.editText?.text.toString().trim()

        return if(name.length < 6)
        {
            binding.signupname.isErrorEnabled = true
            binding.signupname.error = "Use at least 5 characters"
            false
        }
        else
        {
            binding.signupname.isErrorEnabled = false
            true
        }
    }

    private fun validateMobile(): Boolean {
        val name = binding.signupmobile.editText?.text.toString().trim()

        return if(name.length < 10)
        {
            binding.signupmobile.isErrorEnabled = true
            binding.signupmobile.error = "Invalid mobile number"
            false
        }
        else
        {
            binding.signupmobile.isErrorEnabled = false
            true
        }
    }


    private fun validateEmail(): Boolean {
        val email = binding.signupemail.editText?.text.toString().trim()
        val noWhite = Regex("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        Log.d("problems","///////////////////////// $email /////////////////////////////////////////////////////////////")
        return if(email.isEmpty())
        {
            binding.signupemail.isErrorEnabled = true
            binding.signupemail.error = "Field can't be empty"
            false
        }
        else if(!email.contains("@") && !email.contains("."))
        {
            binding.signupemail.isErrorEnabled = true
            binding.signupemail.error = "Enter a valid email"
            false
        }
        else if (email.length < 6)
        {
            binding.signupemail.isErrorEnabled = true
            binding.signupemail.error = "Use at least 5 characters"
            false
        }
        else if(!noWhite.containsMatchIn(email))
        {
            binding.signupemail.isErrorEnabled = true
            binding.signupemail.error = "Enter valid email"
            false
        }
        else
        {
            binding.signupemail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.signuppassword.editText?.text.toString().trim()
        Log.d("problems","///////////////////////// $password /////////////////////////////////////////////////////////////")
        val noWhites = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        return when {
            password.isEmpty() -> {
                binding.signuppassword.error = "Field can't be Empty"
                false
            }
            !noWhites.containsMatchIn(password) -> {
                binding.signuppassword.error = "Password is too weak"
                false
            }
            else -> {
                binding.signuppassword.isErrorEnabled = false
                true
            }
        }
    }

}