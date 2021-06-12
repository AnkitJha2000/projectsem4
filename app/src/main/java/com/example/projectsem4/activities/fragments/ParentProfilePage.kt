package com.example.projectsem4.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.adapters.UserAdapter
import com.example.projectsem4.databinding.FragmentParentProfilePageBinding
import com.google.firebase.platforminfo.UserAgentPublisher

class ParentProfilePage : Fragment() {
    private lateinit var binding : FragmentParentProfilePageBinding
    private lateinit var viewModel : FirebaseAuthViewModel
    private lateinit var User : UserAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentProfilePageBinding.inflate(inflater, container, false)

        return binding.root
    }
}