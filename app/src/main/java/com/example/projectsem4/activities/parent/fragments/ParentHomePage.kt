package com.example.projectsem4.activities.parent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.databinding.FragmentParentHomePageBinding

class ParentHomePage : Fragment() {
    private lateinit var binding : FragmentParentHomePageBinding
    private val viewModel : FirebaseAuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}