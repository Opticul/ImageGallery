package com.example.imagegallery.API

import com.example.imagegallery.data.photodata.unsplash.UnsplashPhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiServiceUnsplash : ImageProvider {

    @GET("search/photos")
    fun fetchAllPhotos(
        @Query("client_id") client_id : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
       // @Query("orientation") orientation : String
    ): Call<UnsplashPhotoList>
}