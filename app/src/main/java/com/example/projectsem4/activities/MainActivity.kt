package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FirebaseAuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = FirebaseAuthViewModel(this.application)

        binding.mainlogout.setOnClickListener{
            print("////////////////////////////// ${viewModel.getUserLiveData().toString()}///////////////////////////////////")
            viewModel.logOut()
            startActivity(Intent(this@MainActivity , LoginSignUpPage::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(currentUser == null)
        {
            startActivity(Intent(this@MainActivity , LoginSignUpPage::class.java))
            finish()
        }
    }

}