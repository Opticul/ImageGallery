package com.example.imagegallery.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageProviderFactory() : RetrofitProvider {

    override fun createRetrofit(imageProvider : ImageSources.ImageSource) : ImageProvider {
        val retrofit = Retrofit.Builder()
            .baseUrl(imageProvider.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(imageProvider.imageProvider)
    }

    fun getService(provider : ImageSources.ImageSource) : ImageProvider {
        return createRetrofit(provider)
    }
}