package com.example.projectsem4.activities.parent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.MainActivity
import com.example.projectsem4.databinding.ActivityParentSignUpPageBinding
import com.google.firebase.auth.FirebaseUser


class ParentSignUpPage : AppCompatActivity() {
    lateinit var binding: ActivityParentSignUpPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentSignUpPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = FirebaseAuthViewModel(this.application)

        binding.parentsignupcreatebtn.setOnClickListener{

            val email = binding.parentsignupemail.editText?.text.toString().trim()
            val password = binding.parentsignuppassword.editText?.text.toString().trim()
            val name = binding.parentsignupname.editText?.text.toString().trim()
            val age = binding.parentsignupage.editText?.text.toString().trim()
            val mobile = binding.parentsignupmobile.editText?.text.toString().trim()

            if(!validateName() || !validateMobile() || !validateEmail() || !validateAge() || !validatePassword())
                return@setOnClickListener
            else {
                viewModel.signup( email , password)
                viewModel.createUser(name , mobile , age , email, "parent")

                viewModel.getUserLiveData()?.observe(this,
                    { firebaseUser ->
                        if (firebaseUser != null) {
                            val intent = Intent(this@ParentSignUpPage , MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })

//                if(viewModel.getUserLiveData() != null) {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }
            }
        }
    }

    private fun validateName(): Boolean {
        val name = binding.parentsignupname.editText?.text.toString().trim()

        return if(name.length < 6)
        {
            binding.parentsignupname.isErrorEnabled = true
            binding.parentsignupname.error = "Use at least 5 characters"
            false
        }
        else
        {
            binding.parentsignupname.isErrorEnabled = false
            true
        }
    }

    private fun validateMobile(): Boolean {
        val name = binding.parentsignupmobile.editText?.text.toString().trim()

        return if(name.length < 10)
        {
            binding.parentsignupmobile.isErrorEnabled = true
            binding.parentsignupmobile.error = "Invalid mobile number"
            false
        }
        else
        {
            binding.parentsignupmobile.isErrorEnabled = false
            true
        }
    }

    private fun validateAge(): Boolean {
        val age = binding.parentsignupage.editText?.text.toString()

        return if(age == "")
        {
            binding.parentsignupage.isErrorEnabled = true
            binding.parentsignupage.error = "Field can't be empty"
            false
        }
        else if(age.toInt() !in 131 downTo 18)
        {
            binding.parentsignupage.isErrorEnabled = true
            binding.parentsignupage.error = "Enter valid age"
            false
        }
        else
        {
            binding.parentsignupage.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.parentsignupemail.editText?.text.toString().trim()
        val noWhite = Regex("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        Log.d("problems","///////////////////////// $email /////////////////////////////////////////////////////////////")
        return if(email.isEmpty())
        {
            binding.parentsignupemail.isErrorEnabled = true
            binding.parentsignupemail.error = "Field can't be empty"
            false
        }
        else if(!email.contains("@") && !email.contains("."))
        {
            binding.parentsignupemail.isErrorEnabled = true
            binding.parentsignupemail.error = "Enter a valid email"
            false
        }
        else if (email.length < 6)
        {
            binding.parentsignupemail.isErrorEnabled = true
            binding.parentsignupemail.error = "Use at least 5 characters"
            false
        }
        else if(!noWhite.containsMatchIn(email))
        {
            binding.parentsignupemail.isErrorEnabled = true
            binding.parentsignupemail.error = "Enter valid email"
            false
        }
        else
        {
            binding.parentsignupemail.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.parentsignuppassword.editText?.text.toString().trim()
        Log.d("problems","///////////////////////// $password /////////////////////////////////////////////////////////////")
        val noWhites = Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        return when {
            password.isEmpty() -> {
                binding.parentsignuppassword.error = "Field can't be Empty"
                false
            }
            !noWhites.containsMatchIn(password) -> {
                binding.parentsignuppassword.error = "Password is too weak"
                false
            }
            else -> {
                binding.parentsignuppassword.isErrorEnabled = false
                true
            }
        }
    }

}
