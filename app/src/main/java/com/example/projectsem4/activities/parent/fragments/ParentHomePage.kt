package com.example.projectsem4.activities.parent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectsem4.databinding.FragmentParentHomePageBinding

class ParentHomePage : Fragment() {
    private lateinit var binding : FragmentParentHomePageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }
}