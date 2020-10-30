package com.example.imagegallery

import android.app.Application
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import com.example.imagegallery.data.photodata.PhotoRepository
import com.example.imagegallery.utilities.InjectorUtils

class MainApplication : Application(), HasAppComponent {

    override val  favoritesRepository by lazy{InjectorUtils.provideFavoritesRepository(this)}
    override val photoRepository = InjectorUtils.providePhotoRepository()

}

interface HasAppComponent {
    val favoritesRepository: FavoritesRepository
    val photoRepository : PhotoRepository
}