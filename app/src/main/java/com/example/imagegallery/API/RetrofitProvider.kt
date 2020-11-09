package com.example.imagegallery.API

interface RetrofitProvider {

    fun createRetrofit(provider : ImageSources.ImageSource) : ImageProvider

}