package com.example.imagegallery.ui.photos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagegallery.HasAppComponent
import com.example.imagegallery.R
import com.example.imagegallery.data.photodata.Photo
import kotlinx.android.synthetic.main.activity_image_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
    }

    private lateinit var thisPhoto: Photo
    private val favoritesRepository by lazy { (applicationContext as HasAppComponent).favoritesRepository }
    private val photoRepository by lazy { (applicationContext as HasAppComponent).photoRepository }

    override fun onStart() {
        super.onStart()

        val itemID = intent.extras?.getString("id")

        thisPhoto = photoRepository.getImageById(itemID ?: "")
        if (thisPhoto.id == "") {
            thisPhoto = favoritesRepository.getFavoriteByID(itemID ?: "")
        }

        //Implement favorite mechanism here
        if (thisPhoto.localFavorite) {
            imageDetailButton.text = getString(R.string.unfavorite)
        } else {
            imageDetailButton.text = getString(R.string.favorite)
        }

        // Picasso.get().load(item).into(image_as_detailed_imageView);
        Glide.with(this).load(thisPhoto.webformatURL).into(image_as_detailed_imageView);
        val setSubtitle = "By ${thisPhoto.user}-${thisPhoto.user_id}"
        val setLikes = "Likes: " + thisPhoto.likes.toString()

        imageTitle.text = thisPhoto.id
        imageSubtitle.text = setSubtitle
        imageURL.text = thisPhoto.webformatURL
        source.text = thisPhoto.source
        likes.text = setLikes
        imageDetailsText.text = thisPhoto.description

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, thisPhoto.webformatURL)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        shareButton.setOnClickListener {
            startActivity(shareIntent)
        }

        imageDetailButton.setOnClickListener {
            if (thisPhoto.id == "") {
                imageDetailButton.visibility = View.GONE
            }
            if (thisPhoto.localFavorite) {
                imageDetailButton.text = getString(R.string.favorite)
                thisPhoto.localFavorite = false
                GlobalScope.launch {
                    favoritesRepository.deleteFavorite(thisPhoto)
                }
            } else {
                thisPhoto.localFavorite = true
                GlobalScope.launch {
                    favoritesRepository.addFavorite(thisPhoto)
                }
                imageDetailButton.text = getString(R.string.unfavorite)
            }
        }
    }
}
