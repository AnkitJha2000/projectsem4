package com.example.projectsem4.activities.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.projectsem4.R
import com.example.projectsem4.ViewModels.FirebaseAuthViewModel
import com.example.projectsem4.databinding.ActivityParentSlotBookingPageBinding

class ParentSlotBookingPage : AppCompatActivity() {
    private lateinit var binding : ActivityParentSlotBookingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParentSlotBookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.date1.setOnClickListener{
            showAlertDialog()
        }
        binding.date2.setOnClickListener{
            showAlertDialog()
        }
        binding.date3.setOnClickListener{
            showAlertDialog()
        }
        binding.date4.setOnClickListener{
            showAlertDialog()
        }
        binding.date5.setOnClickListener{
            showAlertDialog()
        }
        binding.date6.setOnClickListener{
            showAlertDialog()
        }
        binding.date7.setOnClickListener{
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage("Do you wanna book slot now? your details will be filled automatically.")
        alertDialog.setPositiveButton(
            "yes"
        ) { _, _ ->
            Toast.makeText(this , "Slot booked. Relax and please be on time", Toast.LENGTH_LONG).show()
            finish()
        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}