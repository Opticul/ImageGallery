package com.example.imagegallery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_data")
data class DBPhoto (
    @PrimaryKey(autoGenerate = true)
    // Values for pixabay Images
    var id : Int,
    val user : String,
    val user_id : Int,
    val favorites : Int,
    val likes : Int,
    val views: Int,
    val downloads: Int,
    val webformatURL: String
)