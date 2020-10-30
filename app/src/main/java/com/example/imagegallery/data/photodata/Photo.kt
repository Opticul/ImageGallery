package com.example.imagegallery.data.photodata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "photo_data")

data class Photo (
    @PrimaryKey(autoGenerate = true)
    // Values for pixabay Images
    var id : Int,
    val user : String,
    val user_id : Int,
    val favorites : Int,
    val likes : Int,
    val views: Int,
    val downloads: Int,
    val webformatURL: String,
    val previewURL : String,
    var localFavorite : Boolean
)