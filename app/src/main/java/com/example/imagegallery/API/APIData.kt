package com.example.imagegallery.API

class APIData() {

    enum class imageSourceTypes { PIXABAY, UNSPLASH }

    //TODO() extract keys to a .gitIgnore file

    fun getSource(type: imageSourceTypes): ImageSources.ImageSource {
        return when (type) {
            imageSourceTypes.PIXABAY -> ImageSources.ImageSource(
                "https://pixabay.com/",
                "18282472-6c502b3572ec282c7e32710e9",
                IApiServicePixabay::class.java
            )
            //TODO Add Unsplash implementation
            imageSourceTypes.UNSPLASH -> ImageSources.ImageSource(
                "https://api.unsplash.com/",
                "u4ZrP87aePukoaHCL1ZMEywtVi6XyrM7wy7MAcsFM6U",
                IApiServiceUnsplash::class.java
            )
        }
    }

}