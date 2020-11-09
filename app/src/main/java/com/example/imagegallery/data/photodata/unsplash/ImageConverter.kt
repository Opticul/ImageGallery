package com.example.imagegallery.data.photodata.unsplash

import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.data.photodata.pixabay.PixabayPhoto
import kotlin.random.Random

class ImageConverter() {

    fun unsplashListToPhotoList(inList : List<UnsplashPhoto>) :List<Photo>{
        val photoList = mutableListOf<Photo>()
        for (unsplashImage in inList){
           photoList.add(unsplashToPhoto(unsplashImage))
          }
        return photoList
    }
    fun unsplashToPhoto(inUnsplash : UnsplashPhoto) : Photo {
        with(inUnsplash){
            return Photo(
                id,
                user.username,
                user.id,
                likes ?: 0,
                urls.full ?: urls.raw ?: urls.regular ?: urls.small,
                urls.small,
                if (description != null && description.isNotEmpty()) description else if (alt_description!= null && alt_description.isNotEmpty()) alt_description else "No Description",
                false,
                "unsplash")
        }
    }

    fun pixabayListToPhotoList(inList : List<PixabayPhoto>) :List<Photo>{
        val photoList = mutableListOf<Photo>()
        for (pixabayPhoto in inList){
            photoList.add(pixabayToPhoto(pixabayPhoto))
        }
        return photoList
    }
    fun pixabayToPhoto(inPhoto : PixabayPhoto) : Photo {
        with(inPhoto){
            return Photo(
                id.toString(),
                user,
                user_id.toString(),
                likes,
                largeImageURL,
                webformatURL,
                "No Description",
                false,
                "pixabay")
        }
    }
}