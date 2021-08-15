package com.example.projectsem4.activities.parent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.parent.ParentSlotBookingPage
import com.example.projectsem4.adapters.CenterRecyclerViewAdapter
import com.example.projectsem4.databinding.FragmentParentNotifierPageBinding

class ParentNotifierPage : Fragment() {
    private lateinit var binding : FragmentParentNotifierPageBinding
    private val viewModel : FirebaseAuthViewModel by activityViewModels()
    private val adapter = CenterRecyclerViewAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentNotifierPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvNotifier.adapter = adapter
        binding.rvNotifier.layoutManager= LinearLayoutManager(context)

        binding.dummycard.setOnClickListener{
            val intent = Intent(context , ParentSlotBookingPage::class.java )
            startActivity(intent)
        }

        viewModel.loadCenterList()

        viewModel.getCenterList().observe(viewLifecycleOwner , {
            if(it != null)
            {
                adapter.differ.submitList(it)
            }
        })
    }

}