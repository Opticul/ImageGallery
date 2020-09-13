package com.example.imagegallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagegallery.data.DBPhoto
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
    }
    private lateinit var thisPhoto : DBPhoto
    override fun onStart() {
        super.onStart()

        val item = intent.extras?.getString("url")
        if (item != null) {
            var retrievedPhoto = MainActivity.imageMap[item]
            if (retrievedPhoto != null) {
                thisPhoto = retrievedPhoto
            }
            if (MainActivity.favoritesArray.contains(item)){
            imageDetailButton.text = "Unfavorite"
            }
        }
       // Picasso.get().load(item).into(image_as_detailed_imageView);
        Glide.with(this).load(item).into(image_as_detailed_imageView);
        val setTitle = "${thisPhoto.id} by ${thisPhoto.user}-${thisPhoto.user_id}"
        val setDownloads = "Downloads: " + thisPhoto.downloads.toString()
        val setViews = "Views: " + thisPhoto.views.toString()
        val setFavorites = "Favorites: " + thisPhoto.favorites.toString()
        val setLikes = "Likes: " + thisPhoto.likes.toString()
        imageTitle.text = setTitle
        imageURL.text = item
        downloads.text = setDownloads
        views.text = setViews
        favorites.text = setFavorites
        likes.text = setLikes

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, item)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        shareButton.setOnClickListener {
            startActivity(shareIntent)
            }

        imageDetailButton.setOnClickListener {
            if (item != null) {
                if (imageDetailButton.text == "Favorite") {
                    MainActivity.favoritesArray.add(item)
                    MainActivity.mPhotoViewModel.addPhoto(thisPhoto)
                    MainActivity.favoritesImageMap.put(item,thisPhoto)
                    imageDetailButton.text = "Unfavorite"
                } else {
                    MainActivity.favoritesArray.remove(item)
                    MainActivity.favoritesImageMap.remove(item)
                    MainActivity.mPhotoViewModel.deletePhoto((thisPhoto))
                    imageDetailButton.text = "Favorite"
                }

            }
        }
    }
}