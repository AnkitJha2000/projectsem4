package com.example.projectsem4.activities.parent

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
    private val IMAGE_REQUEST_CODE = 100
    private var filepath : Uri? = null

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

                 picasso.load(it.getUserProfileUrl()).placeholder(R.drawable.ic_baseline_timer_24).into(binding.parentEditProfilePicture)

             })
        })

        binding.parentSaveDataBtn.setOnClickListener{
            viewModel.getUserLiveData()?.observe(this , {user ->
                viewModel.getUserInfoLiveData().observe(this , {adapter->
                    viewModel.getProfileUrl().observe(this , { url->
                        viewModel.updateUser("parent" ,
                            user.uid ,
                            binding.parentEditName.editText?.text.toString(),
                            binding.parentEditMobileNumber.editText?.text.toString() ,
                            binding.parentEditAge.editText?.text.toString() ,
                            url,
                            adapter.getUserEmail())
                        Log.d(TAG, "onCreate: the user was updated !! ")
                    })
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
            pickImageFromGallery()
        }

    }

    fun pickImageFromGallery(){
        startActivityForResult(Intent(Intent.ACTION_PICK).setType("image/*") , IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val builder = ProgressDialog(this)

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            builder.setTitle("Please Wait")
            builder.setMessage("Uploading Profile Photo.......")
            builder.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            if(data == null || data.data == null)
            {
                return
            }
            else
            {
                binding.parentEditDetailsProgressBar.isIndeterminate = true
                binding.parentEditDetailsProgressBar.isVisible = true

                filepath = data.data
                binding.parentEditProfilePicture.setImageURI(data.data)
                viewModel.getUserLiveData()?.observe(this , { user->

                    if( filepath != null)
                    {
                        viewModel.uploadImage(filepath!!, user.uid , "parent")
                        viewModel.getUploadImageError().observe(this , {
                            if(it == "uploading")
                            {
                                binding.parentEditDetailsProgressBar.isVisible = true
                                builder.show()
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            }
                            if(it == "done")
                            {
                                builder.dismiss()
                                binding.parentEditDetailsProgressBar.isVisible = false
                                Toast.makeText(this, "$it Successfully", Toast.LENGTH_SHORT).show()
                            }
                            if(it == "failed")
                            {
                                builder.dismiss()
                                binding.parentEditDetailsProgressBar.isVisible = false
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            }
                        })
                    }

//                    viewModel.getErrorLiveData().observe(this, {
//                        if(it == null)
//                        {
//                            Toast.makeText(this, "image uploaded successfully", Toast.LENGTH_SHORT).show()
//                        }
//                        else
//                        {
//                            Toast.makeText(this, it , Toast.LENGTH_SHORT).show()
//                        }
//
//                    })

                })

            }

        }

    }

}