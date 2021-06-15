package com.example.projectsem4.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.application.VaccinationApplication
import com.example.projectsem4.databinding.FragmentParentProfilePageBinding
import com.squareup.picasso.Picasso

class ParentProfilePage(private val repository: AuthUserRepository) : Fragment() {

    private lateinit var binding : FragmentParentProfilePageBinding

    private val viewModel : FirebaseAuthViewModel by viewModels {
        FirebaseViewModelFactory( ( requireActivity().application as VaccinationApplication).repository)
    }

    // private lateinit var latestViewModel : FirebaseAuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // val viewModelFactory = FirebaseViewModelFactory(repository)

        // latestViewModel = ViewModelProvider(this , viewModelFactory).get(FirebaseAuthViewModel::class.java)

        // Log.d("repoUsed", "onViewCreated:" + repository.getUserLiveData().toString())

        viewModel.getUserLiveData()?.observe(viewLifecycleOwner , { uid ->

            viewModel.getUserInfoLiveData().observe(viewLifecycleOwner, {

                    // viewModel.getUser("parent" , uid.uid)

                    print("/// ${it.getUserEmail()} ////////////////////////////////////////////////////////")
                    Log.d("ankit when repo is null", "onViewCreated: $it")
                    binding.parentName.text = it.getUserName()
                    binding.parentAge.text = it.getUserAge()
                    binding.parentEmail.text = it.getUserEmail()
                    binding.parentMobileNUmber.text = it.getUserMobile()

                    val picasso = Picasso.Builder(requireContext())
                        .listener { _, _, e -> e.printStackTrace() }
                        .build()

                    picasso.load("http://goo.gl/gEgYUd").placeholder(R.drawable.ic_baseline_timer_24).into(binding.parentProfilePicture)

            })
        })

        binding.parentProfileEditBtn.setOnClickListener{
            // startActivity(Intent(activity , ))
        }

    }
}





