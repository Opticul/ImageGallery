package com.example.imagegallery.data.photodata.pixabay

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class PixabayPhoto (

    // Values for pixabay Images
    var id : Int,
    val user : String,
    val user_id : Int,
    val favorites : Int,
    val likes : Int,
    val views: Int,
    val downloads: Int,
    val webformatURL: String,
    val largeImageURL: String,
    var localFavorite : Boolean
)