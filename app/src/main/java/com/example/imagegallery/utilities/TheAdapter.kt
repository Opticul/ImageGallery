package com.example.imagegallery.utilities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.MainActivity
import com.example.imagegallery.R
import com.example.imagegallery.ui.photos.ImageDetailActivity
import kotlinx.android.synthetic.main.image_as_part_of_list.view.*

class TheAdapter(val inURLs: ArrayList<String>) : RecyclerView.Adapter<TheAdapter.TextHolder>() {

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
            //Use Picasso for jsonplaceholder requests, they bug with Glide
            //Picasso.get().load(item).into(view.image_as_part_of_list_imageView);
            Glide.with(view).load(item).into(view.image_as_part_of_list_imageView);
            if (MainActivity.favoritesArray.contains(item)){
                view.favoriteHeart.visibility = View.VISIBLE
            } else {
                view.favoriteHeart.visibility = View.INVISIBLE
            }
        }

        override fun onClick(v: View?) {
            val detailIntent = Intent(view.context, ImageDetailActivity::class.java)
            detailIntent.putExtra("url", item)
            ContextCompat.startActivity(view.context, detailIntent, null)
        }

        override fun onLongClick(v: View?): Boolean {
            if (MainActivity.favoritesArray.contains(item)){
                MainActivity.favoritesArray.remove(item)
                MainActivity.favoritesImageMap.remove(item)

                val thisPhoto = MainActivity.favoritesImageMap[item]
                if (thisPhoto != null) {
                    MainActivity.mPhotoViewModel.deleteFavorite(thisPhoto)
                }
                view.favoriteHeart.visibility = View.INVISIBLE
            } else {
                val thisPhoto = MainActivity.imageMap[item]
                if (thisPhoto != null) {
                    MainActivity.mPhotoViewModel.addFavorite(thisPhoto)
                    MainActivity.favoritesImageMap[item] = thisPhoto
                    MainActivity.favoritesArray.add(item)
                    view.favoriteHeart.visibility = View.VISIBLE
                }

            }
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

    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        val thisString = inURLs[position]
        holder.bindItem(thisString)
    }
}