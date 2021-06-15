package com.example.projectsem4.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.ViewModels.FirebaseViewModelFactory
import com.example.projectsem4.activities.fragments.ParentHomePage
import com.example.projectsem4.activities.fragments.ParentNotifierPage
import com.example.projectsem4.activities.fragments.ParentProfilePage
import com.example.projectsem4.activities.repository.AuthUserRepository
import com.example.projectsem4.application.VaccinationApplication
import com.example.projectsem4.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel : FirebaseAuthViewModel by viewModels {
        FirebaseViewModelFactory((application as VaccinationApplication).repository)
    }

    private val repository : AuthUserRepository by lazy{
        Log.d("repositoryMain", "repo was created !!!!!!")
        AuthUserRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        viewModel.getUserLiveData()?.observe(this , { firebaseuid ->
            viewModel.getUser("parent" , firebaseuid.uid)
            Log.d("debug_profile_fragment" , firebaseuid.uid )
        })

        binding.mainlogout.setOnClickListener{
            print("////////////////////////////// ${viewModel.getUserLiveData().toString()}///////////////////////////////////")
            viewModel.logOut()
            startActivity(Intent(this@MainActivity , LoginSignUpPage::class.java))
            finish()
        }

        loadFragment(ParentHomePage())

        binding.mainBottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int ,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")

                if(newIndex == 0)
                {
                    loadFragment(ParentHomePage())
                }
                else if(newIndex == 1)
                {
                    loadFragment(ParentNotifierPage())
                }
                else if(newIndex == 2)
                {
                    loadFragment(ParentProfilePage(repository))
                }
                else{
                    loadFragment(ParentHomePage())
                }
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.parentFragmentContainer, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(currentUser == null)
        {
            startActivity(Intent(this@MainActivity , LoginSignUpPage::class.java))
            finish()
        }


    }

}