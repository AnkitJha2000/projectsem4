package com.example.projectsem4.activities.parent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.activities.LoginSignUpPage
import com.example.projectsem4.activities.parent.ParentEditDetailsPage
import com.example.projectsem4.activities.parent.ParentSlotsInfo
import com.example.projectsem4.databinding.FragmentParentProfilePageBinding
import com.squareup.picasso.Picasso

class ParentProfilePage : Fragment() {

    private lateinit var binding : FragmentParentProfilePageBinding

    private val viewModel : FirebaseAuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentParentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

            viewModel.getUserInfoLiveData().observe(viewLifecycleOwner, {

                    print("/// ${it.getUserEmail()} ////////////////////////////////////////////////////////")
                    binding.parentName.text = it.getUserName()
                    binding.parentAge.text = it.getUserAge()
                    binding.parentEmail.text = it.getUserEmail()
                    binding.parentMobileNUmber.text = it.getUserMobile()

                    val picasso = Picasso.Builder(requireContext())
                        .listener { _, _, e -> e.printStackTrace() }
                        .build()

                    picasso.load(it.getUserProfileUrl()).placeholder(R.drawable.ic_baseline_timer_24).into(binding.parentProfilePicture)

            })

        binding.parentLogoutBtn.setOnClickListener{

            print("////////////////////////////// ${viewModel.getUserLiveData().toString()}///////////////////////////////////")
            viewModel.logOut()
            startActivity(Intent(requireContext() , LoginSignUpPage::class.java))
            activity?.finish()

        }

        binding.parentProfileEditBtn.setOnClickListener{
            startActivity(Intent(activity , ParentEditDetailsPage::class.java))
        }

        binding.parentSlotInfo.setOnClickListener{
            startActivity(Intent(activity , ParentSlotsInfo::class.java))
        }

    }
}





