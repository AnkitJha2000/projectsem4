package com.example.projectsem4.activities.parent

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.databinding.ActivityParentEditDetailsPageBinding
import com.squareup.picasso.Picasso

class ParentEditDetailsPage : AppCompatActivity() {

    private lateinit var binding : ActivityParentEditDetailsPageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentEditDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = FirebaseViewModelFactory(AuthUserRepository())
        viewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)

        viewModel.getUserLiveData()?.observe(this , { user->
             viewModel.getUser("parent" , user.uid)
             viewModel.getUserInfoLiveData().observe(this , {

                 binding.parentEditName.editText?.setText(it.getUserName())
                 binding.parentEditMobileNumber.editText?.setText(it.getUserMobile())
                 binding.parentEditAge.editText?.setText(it.getUserAge())

                 val picasso = Picasso.Builder(this)
                     .listener { _, _, e -> e.printStackTrace() }
                     .build()

                 picasso.load("http://goo.gl/gEgYUd").placeholder(R.drawable.ic_baseline_timer_24).into(binding.parentEditProfilePicture)

             })
        })

        binding.parentSaveDataBtn.setOnClickListener{
            viewModel.getUserLiveData()?.observe(this , {user ->
                viewModel.getUserInfoLiveData().observe(this , {adapter->
                    viewModel.updateUser("parent" ,
                        user.uid ,
                        binding.parentEditName.editText?.text.toString(),
                        binding.parentEditMobileNumber.editText?.text.toString() ,
                        binding.parentEditAge.editText?.text.toString() ,
                        adapter.getUserProfileUrl() ,
                        adapter.getUserEmail())
                    Log.d(TAG, "onCreate: the user was updated !! ")
                })
            })
            viewModel.getErrorLiveData().observe(this , {
                if(it == null)
                {
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                }
            })
        }


        // 16/06/2021
        binding.parentEditProfilePictureBtn.setOnClickListener{
            // uploadImage()
        }

    }

}