package com.example.imagegallery.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.R
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.ui.favorites.FavoriteTextHolder

class TheFavoriteAdapter(
    private val inPhotos: LiveData<List<Photo>>,
    private val clickListener: (FavoriteTextHolder.ClickType) -> Unit
) : RecyclerView.Adapter<FavoriteTextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTextHolder {
        return FavoriteTextHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_as_part_of_list, parent, false),
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

    override fun onBindViewHolder(holderFavorite: FavoriteTextHolder, position: Int) {
        val thisPhoto = inPhotos.value?.get(position)
        if (thisPhoto != null) {
            holderFavorite.bindItem(thisPhoto)
    }
    }

}