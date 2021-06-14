package com.example.projectsem4.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.databinding.FragmentParentProfilePageBinding
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.platforminfo.UserAgentPublisher

class ParentProfilePage : Fragment() {
    private lateinit var binding : FragmentParentProfilePageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    private lateinit var repository: AuthUserRepository
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = AuthUserRepository()

        val viewModelFactory = FirebaseViewModelFactory(repository)

        viewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)

        viewModel.getUserLiveData()?.observe(viewLifecycleOwner , { uid ->
            viewModel.getUser("parent" , uid.uid)
            viewModel.getUserInfoLiveData()?.observe(viewLifecycleOwner, { it ->

                print("/// ${it.getUserEmail()} ////////////////////////////////////////////////////////")
                it.getUserEmail()?.let { it1 -> Log.d("debug_profile_fragment", it1) }
                binding.parentName.text = it.getUserName()
                binding.parentAge.text = it.getUserAge()
                binding.parentEmail.text = it.getUserEmail()
                binding.parentMobileNUmber.text = it.getUserMobile()

            })
        })
    }
}





