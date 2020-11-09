package com.example.imagegallery.API

import com.example.imagegallery.data.photodata.pixabay.PixabayPhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiServicePixabay : ImageProvider {

    @GET("api")
    fun fetchAllPhotos(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("image_type") image_type: String,
        @Query("per_page") per_page: Int
    ): Call<PixabayPhotoList>
}