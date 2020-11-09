package com.example.imagegallery.data.photodata.unsplash


data class UnsplashPhoto (
    val id: String,
    val user : UnsplashUser,
    val urls: UnsplashUrls,
    val likes: Int?,
    val description: String?,
    val alt_description: String?


)