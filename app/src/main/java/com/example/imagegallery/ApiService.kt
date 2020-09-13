package com.example.imagegallery

import com.example.imagegallery.data.DBPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url as Url1

//Used for Jsonplaceholder page
interface ApiService {
    @GET("/photos")
    fun fetchAllPhotos(): Call<List<DBPhoto>>
}

interface ApiServicePixabayCustom {

    @GET("api")

    fun fetchAllPhotos(
     @Query("key") key : String,
     @Query("q") q : String,
    @Query("image_type") image_type : String,
    @Query("per_page") per_page : Int)
            : Call<DBPhotoList>

}
