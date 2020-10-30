package com.example.imagegallery.ui.photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.data.favoritedata.FavoritesDB
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*


class PhotoViewModel(application: Application) : AndroidViewModel(application){

   // val readAllData: LiveData<List<DBPhoto>>
   val favorites: List<Photo>

    private val repository : FavoritesRepository

    init {
        val photoDao = FavoritesDB.getDatabase((application)).favoritesDao()
        repository = FavoritesRepository(photoDao)
        favorites = repository.getFavorites()
    }

    fun addFavorite(photo: Photo){
        GlobalScope.launch(Dispatchers.IO) {
            repository.addFavorite(photo)
        }
    }

    fun deleteFavorite(photo: Photo){
        GlobalScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(photo)
        }
    }


}