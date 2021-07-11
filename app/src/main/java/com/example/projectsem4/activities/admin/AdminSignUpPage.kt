package com.example.projectsem4.activities.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.repository.AuthUserRepository
import com.example.projectsem4.databinding.ActivityAdminSignUpPageBinding

class AdminSignUpPage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminSignUpPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    private lateinit var repository: AuthUserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminSignUpPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        repository = AuthUserRepository()

        val viewModelFactory = FirebaseViewModelFactory(repository)

        viewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)

        binding.adminSignUpLoginToSignUp.setOnClickListener {
            startActivity(Intent(this@AdminSignUpPage , AdminLoginPage::class.java))
            finish()
        }

        binding.adminSignUpCreateBtn.setOnClickListener{
            if(!validateName() || !validateMobile() || !validateEmail() || !validatePassword())
                return@setOnClickListener
            else
            {
                val email = binding.adminSignUpEmail.editText?.text.toString()
                val location = binding.adminSignUpLocation.editText?.text.toString()
                val password = binding.adminSignUpPassword.editText?.text.toString()
                val name = binding.adminSignUpCenterName.editText?.text.toString()

            }
        }
    }

    private fun validateName(): Boolean {
        val name = binding.adminSignUpCenterName.editText?.text.toString().trim()

        return if(name.length < 6)
        {
            binding.adminSignUpCenterName.isErrorEnabled = true
            binding.adminSignUpCenterName.error = "Use at least 5 characters"
            false
        }
        else
        {
            binding.adminSignUpCenterName.isErrorEnabled = false
            true
        }
    }

    private fun validateMobile(): Boolean {
        val name = binding.adminSignUpLocation.editText?.text.toString().trim()

        return if(name.length < 10)
        {
            binding.adminSignUpLocation.isErrorEnabled = true
            binding.adminSignUpLocation.error = "Invalid mobile number"
            false
        }
        else
        {
            binding.adminSignUpLocation.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.adminSignUpEmail.editText?.text.toString().trim()
        val noWhite = Regex("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        Log.d("problems","///////////////////////// $email /////////////////////////////////////////////////////////////")
        return if(email.isEmpty())
        {
            binding.adminSignUpEmail.isErrorEnabled = true
            binding.adminSignUpEmail.error = "Field can't be empty"
            false
        }
        else if(!email.contains("@") && !email.contains("."))
        {
            binding.adminSignUpEmail.isErrorEnabled = true
            binding.adminSignUpEmail.error = "Enter a valid email"
            false
        }
        else if (email.length < 6)
        {
            binding.adminSignUpEmail.isErrorEnabled = true
            binding.adminSignUpEmail.error = "Use at least 5 characters"
            false
        }
        else if(!noWhite.containsMatchIn(email))
        {
            binding.adminSignUpEmail.isErrorEnabled = true
            binding.adminSignUpEmail.error = "Enter valid email"
            false
        }
        else
        {
            binding.adminSignUpEmail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.adminSignUpPassword.editText?.text.toString().trim()
        Log.d("problems","///////////////////////// $password /////////////////////////////////////////////////////////////")
        val noWhites = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        return when {
            password.isEmpty() -> {
                binding.adminSignUpPassword.error = "Field can't be Empty"
                false
            }
            !noWhites.containsMatchIn(password) -> {
                binding.adminSignUpPassword.error = "Password is too weak"
                false
            }
            else -> {
                binding.adminSignUpPassword.isErrorEnabled = false
                true
            }
        }
    }

}