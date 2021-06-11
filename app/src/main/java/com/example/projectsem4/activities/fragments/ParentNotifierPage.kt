package com.example.projectsem4.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectsem4.R
import com.example.projectsem4.databinding.FragmentParentHomePageBinding
import com.example.projectsem4.databinding.FragmentParentNotifierPageBinding

class ParentNotifierPage : Fragment() {
    private lateinit var binding : FragmentParentNotifierPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentNotifierPageBinding.inflate(inflater, container, false)
        return binding.root



    }
}