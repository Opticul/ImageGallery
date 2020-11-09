package com.example.imagegallery.data.photodata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "photo_data")

data class Photo (
   // @PrimaryKey(autoGenerate = true)
    // Values for pixabay Images
    @PrimaryKey
    val id : String,
    val user : String,
    val user_id : String,
    val likes : Int,
    val webformatURL: String,
    val largeImageURL : String,
    val description : String = "No Description",
    var localFavorite : Boolean,
    val source : String
)