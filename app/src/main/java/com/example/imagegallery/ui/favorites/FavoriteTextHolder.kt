package com.example.imagegallery.ui.favorites

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.data.photodata.Photo
import kotlinx.android.synthetic.main.image_as_part_of_list.view.*

class FavoriteTextHolder(
    private val view: View,
    private val clickListener: (ClickType) -> Unit,
) : RecyclerView.ViewHolder(view) {

    sealed class ClickType {
        data class Favourites(val itemId: Int) : ClickType()
        data class Item(val viewContext: Context, val itemId: Int) : ClickType()
    }

    fun bindItem(item: Photo) {

        Glide.with(view).load(item.previewURL).into(view.image_as_part_of_list_imageView);
        view.setOnLongClickListener {
            clickListener(ClickType.Favourites(item.id)); true
        }
        view.setOnClickListener {
            clickListener(
                ClickType.Item(
                    itemId = item.id,
                    viewContext = view.context
                )
            )
        }
    }


}


