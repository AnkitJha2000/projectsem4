package com.example.projectsem4.activities.parent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.MainActivity
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.databinding.ActivityParentLoginPageBinding
import com.google.firebase.database.core.Tag

class ParentLoginPage : AppCompatActivity() {
    private lateinit var binding : ActivityParentLoginPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    private lateinit var repository: AuthUserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParentLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        repository = AuthUserRepository()

        val viewModelFactory = FirebaseViewModelFactory(repository)

        viewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)


        binding.parentlogintosignup.setOnClickListener {
            val intent = Intent(this@ParentLoginPage, ParentSignUpPage::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginbtn.setOnClickListener {
            if (!validateEmail() || !validatePassword())
                return@setOnClickListener
            else {
                val email = binding.loginemail.editText?.text.toString().trim()
                val password = binding.loginpassword.editText?.text.toString().trim()

                viewModel.login(email, password)

                print("//////////////////////////////////// this line should execute //////////////////////////////////////")

                viewModel.getUserLiveData()?.observe(this,
                    { firebaseUser ->
                        if (firebaseUser != null) {
                            Toast.makeText(this , "Login Successful !", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ParentLoginPage, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            viewModel.getErrorLiveData().observe(this , {
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            })
                        }
                    })
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.loginemail.editText?.text.toString().trim()
        val noWhite = Regex("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        Log.d("problems","///////////////////////// $email /////////////////////////////////////////////////////////////")
        return if(email.isEmpty())
        {
            binding.loginemail.isErrorEnabled = true
            binding.loginemail.error = "Field can't be empty"
            false
        }
        else if(!email.contains("@") && !email.contains("."))
        {
            binding.loginemail.isErrorEnabled = true
            binding.loginemail.error = "Enter a valid email"
            false
        }
        else if (email.length < 6)
        {
            binding.loginemail.isErrorEnabled = true
            binding.loginemail.error = "Use at least 5 characters"
            false
        }
        else if(!noWhite.containsMatchIn(email))
        {
            binding.loginemail.isErrorEnabled = true
            binding.loginemail.error = "Enter valid email"
            false
        }
        else
        {
            binding.loginemail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.loginpassword.editText?.text.toString().trim()
        Log.d("problems","///////////////////////// $password /////////////////////////////////////////////////////////////")
        val noWhites = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        return when {
            password.isEmpty() -> {
                binding.loginpassword.error = "Field can't be Empty"
                false
            }
            !noWhites.containsMatchIn(password) -> {
                binding.loginpassword.error = "Password is too weak"
                false
            }
            else -> {
                binding.loginpassword.error = null
                binding.loginpassword.isErrorEnabled = false
                true
            }
        }
    }

}