package com.example.imagegallery.utilities

import android.app.Application
import android.content.Context
import com.example.imagegallery.HasAppComponent
import com.example.imagegallery.data.favoritedata.FavoritesDB
import com.example.imagegallery.data.favoritedata.FavoritesDao
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import com.example.imagegallery.data.photodata.PhotoDao
import com.example.imagegallery.data.photodata.PhotoRepository
import com.example.imagegallery.ui.favorites.FavoritesViewModelFactory
import com.example.imagegallery.ui.photos.PhotoViewModelFactory

//Replace with dagger later

object InjectorUtils {

    // The repositories should only be created by application data holders.
    fun provideFavoritesRepository(context : Context) : FavoritesRepository {val favoritesDao = FavoritesDB.getDatabase((context)).favoritesDao()
        return FavoritesRepository(favoritesDao)
    }

    fun providePhotoRepository() : PhotoRepository {
        return PhotoRepository()
    }

    fun providePhotosViewModelFactory(context: Context) : PhotoViewModelFactory {
        return PhotoViewModelFactory((context.applicationContext as HasAppComponent).photoRepository)
    }

    fun provideFavoritesViewModelFactory(context: Context) : FavoritesViewModelFactory {

        return FavoritesViewModelFactory((context.applicationContext as HasAppComponent).favoritesRepository)
    }

}