package com.example.imagegallery.data.photodata.unsplash

import com.example.imagegallery.data.photodata.Photo

data class UnsplashPhotoList(val results: List<UnsplashPhoto>) {
    private val imageConverter = ImageConverter()
    fun getPhotoList() : List<Photo> {
        return imageConverter.unsplashListToPhotoList(results)
    }
}