package com.example.projectsem4.activities.parent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.MainActivity
import com.example.projectsem4.databinding.ActivityParentLoginPageBinding

class ParentLoginPage : AppCompatActivity() {
    private lateinit var binding : ActivityParentLoginPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParentLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = FirebaseAuthViewModel(this.application)

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
                            val intent = Intent(this@ParentLoginPage, MainActivity::class.java)
                            startActivity(intent)
                            finish()
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