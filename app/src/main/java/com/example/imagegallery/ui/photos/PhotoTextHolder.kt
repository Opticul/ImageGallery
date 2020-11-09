package com.example.imagegallery.ui.photos

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.data.photodata.Photo
import kotlinx.android.synthetic.main.image_as_part_of_list.view.*

class PhotoTextHolder(
    private val view: View,
    private val clickListener: (ClickType) -> Unit,
) : RecyclerView.ViewHolder(view) {

    sealed class ClickType {
        data class ChangeFavoriteStatus(val photo: Photo) : ClickType()
        data class Item(val viewContext: Context, val itemId: String) : ClickType()
    }

    fun bindItem(item: Photo) {

        Glide.with(view).load(item.webformatURL).into(view.image_as_part_of_list_imageView);
        if (item.localFavorite) {view.favoriteHeart.visibility = View.VISIBLE}
        else {view.favoriteHeart.visibility = View.INVISIBLE}
        view.setOnLongClickListener {
            if (item.localFavorite) {
                view.favoriteHeart.visibility = View.INVISIBLE
            } else {
                view.favoriteHeart.visibility = View.VISIBLE
            }
            clickListener(ClickType.ChangeFavoriteStatus(item)); true
        }

        view.setOnClickListener {
            clickListener(
                ClickType.Item(
                    itemId = item.id,
                    viewContext = view.context,

                )
            )
        }
    }
}


