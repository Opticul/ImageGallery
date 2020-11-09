package com.example.imagegallery.API


class ImageSources {
    private val apiData = APIData()

     data class ImageSource(
        val baseURL: String,
        val key: String,
        val imageProvider: Class<out ImageProvider>
    )

    fun getSource(type: APIData.imageSourceTypes): ImageSource {
        return apiData.getSource(type)
    }
}
