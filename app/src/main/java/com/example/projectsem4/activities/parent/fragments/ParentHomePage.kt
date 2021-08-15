package com.example.projectsem4.activities.parent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.adapters.ParentHomePageViewPagerAdapter
import com.example.projectsem4.databinding.FragmentParentHomePageBinding

class ParentHomePage : Fragment() {
    private lateinit var binding : FragmentParentHomePageBinding
    private val viewModel : FirebaseAuthViewModel by activityViewModels()
    private var imageList = mutableListOf<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageList.add(0 , R.drawable.homepagevaccinate1)
        imageList.add(1 , R.drawable.prevent)
        imageList.add(2 , R.drawable.vaccinate1)
        imageList.add(3 , R.drawable.washhands)
        imageList.add(4 , R.drawable.wearmask1)
        imageList.add(5 , R.drawable.vaccinateques1)
        imageList.add(6 , R.drawable.stayhomeedited)
        imageList.add(7 , R.drawable.vaccinate2)
        imageList.add(8 , R.drawable.getvaccine)

        binding.parentHomeViewPager.adapter = ParentHomePageViewPagerAdapter(imageList)
        binding.indicator.setViewPager(binding.parentHomeViewPager)

        binding.ivQuesYes.setOnClickListener{
            binding.ivQues.visibility = View.GONE
            binding.ivQuesNo.visibility = View.GONE
            binding.ivQuesYes.visibility = View.GONE
            binding.tvques.visibility = View.GONE
        }

        binding.ivQuesNo.setOnClickListener{
            Toast.makeText(context, "Please get vaccinated !! Hurry, Go to vaccine section book your vaccine!!", Toast.LENGTH_SHORT).show()
        }
    }
}