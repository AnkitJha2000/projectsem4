package com.example.projectsem4.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsem4.R
import com.example.projectsem4.activities.parent.fragments.ParentNotifierPage

class CenterRecyclerViewAdapter() : RecyclerView.Adapter<CenterRecyclerViewAdapter.CenterViewHolder>() {

    inner class CenterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val centername = itemView.findViewById<TextView>(R.id.itemCenterName)
        val centerLocation = itemView.findViewById<TextView>(R.id.itemCenterLocation)
        val cost = itemView.findViewById<TextView>(R.id.itemPaidOrFree)
        val slotsAvailable = itemView.findViewById<TextView>(R.id.itemSlotsAvailable)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        return CenterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notifier_centers,parent , false))
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val centerName = holder.centername
        val centerLocation = holder.centerLocation
        val cost = holder.cost
        val slotsAvailable = holder.slotsAvailable

        // differ list checks for change first and then updates the value on its own////////////////
        val data = differ.currentList[position]

        holder.apply {
            centerName.text = data.centerName
            centerLocation.text = data.centerLocation
            cost.text = data.cost
            slotsAvailable.text ="Slots available :" + data.slotsAvailable
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<CenterInfoAdapter>(){
        override fun areItemsTheSame(oldItem: CenterInfoAdapter, newItem: CenterInfoAdapter): Boolean {
            return (oldItem.centerName == newItem.centerName)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CenterInfoAdapter, newItem: CenterInfoAdapter): Boolean {
            return oldItem == newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBack)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}