package com.example.imagegallery.data.photodata.pixabay

import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.data.photodata.unsplash.ImageConverter

class PixabayPhotoList (val hits: List<PixabayPhoto>) {
    private val imageConverter = ImageConverter()
    fun getPhotoList() : List<Photo> {
        return imageConverter.pixabayListToPhotoList(hits)
    }
}