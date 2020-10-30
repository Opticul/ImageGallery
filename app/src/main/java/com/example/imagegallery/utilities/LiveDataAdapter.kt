package com.example.imagegallery.utilities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.R
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.ui.favorites.FavoriteTextHolder
import com.example.imagegallery.ui.photos.PhotoTextHolder
import com.example.imagegallery.ui.photos.TestImageDetailActivity

class LiveDataAdapter(
    private val inPhotos: LiveData<List<Photo>>,
    private val clickListener : (PhotoTextHolder.ClickType) -> Unit
) : RecyclerView.Adapter<PhotoTextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : PhotoTextHolder {
        return PhotoTextHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_as_part_of_list,parent,false),
            clickListener
        )
    }

    override fun getItemCount(): Int {
        return if (inPhotos.value == null) {
            0
        } else {
            inPhotos.value!!.size
        }
    }

    override fun onBindViewHolder(holder: PhotoTextHolder, position: Int) {
        val thisString = inPhotos.value?.get(position)
        if (thisString != null) {
            holder.bindItem(thisString)
        }
    }
  }
