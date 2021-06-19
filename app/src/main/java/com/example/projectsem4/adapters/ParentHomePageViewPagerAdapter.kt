package com.example.projectsem4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsem4.R

class ParentHomePageViewPagerAdapter(private var imageList : List<Int>) : RecyclerView.Adapter<ParentHomePageViewPagerAdapter.ParentViewPager2Holder>() {

    inner class ParentViewPager2Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.ivItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewPager2Holder {
        return ParentViewPager2Holder(LayoutInflater.from(parent.context).inflate(R.layout.parent_home_page_viewpager_item , parent , false))
    }

    override fun onBindViewHolder(holder: ParentViewPager2Holder, position: Int) {
        holder.image.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}