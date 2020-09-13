package com.example.imagegallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_detail.*
import kotlinx.android.synthetic.main.image_as_part_of_list.view.*

class TheFavoriteAdapter(val inURLs: ArrayList<String>) : RecyclerView.Adapter<TheFavoriteAdapter.TextHolder>() {

    inner class TextHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener,
        View.OnLongClickListener {
        var view: View = v
        var item: String = ""

        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener(this)
        }

        fun bindItem(item: String) {
            this.item = item
           // Picasso.get().load(item).into(view.image_as_part_of_list_imageView);
            Glide.with(view).load(item).into(view.image_as_part_of_list_imageView);
        }

        override fun onClick(v: View?) {
            val detailIntent = Intent(view.context, ImageDetailActivity::class.java)
            detailIntent.putExtra("url", item)
            ContextCompat.startActivity(view.context, detailIntent, null)
        }

        override fun onLongClick(v: View?): Boolean {
           //No need to check, everything in favorites are favorites
            val thisPhoto = MainActivity.favoritesImageMap[item]
            if (thisPhoto != null) {
                MainActivity.mPhotoViewModel.deletePhoto(thisPhoto)
            }
            MainActivity.favoritesImageMap.remove(item)
            MainActivity.favoritesArray.remove(item)

            var myIntent = Intent(view.context,FavoriteActivity::class.java)
            ContextCompat.startActivity(view.context, myIntent,null)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {
        return TextHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_as_part_of_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return inURLs.count()
    }

    override fun onBindViewHolder(holder: TheFavoriteAdapter.TextHolder, position: Int) {
        val thisString = inURLs[position]
        holder.bindItem(thisString)
    }

}