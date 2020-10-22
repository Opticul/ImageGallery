package com.example.imagegallery.ui.photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.imagegallery.data.photodata.DBPhoto
import com.example.imagegallery.data.favoritedata.FavoritesDB
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*


class PhotoViewModel(application: Application) : AndroidViewModel(application){

   // val readAllData: LiveData<List<DBPhoto>>
   val favorites: List<DBPhoto>

    private val repository : FavoritesRepository

    init {
        var photoDao = FavoritesDB.getDatabase((application)).photoDao()
        repository = FavoritesRepository(photoDao)
        favorites = repository.getFavorites()
    }

    fun addFavorite(dbPhoto: DBPhoto){
        GlobalScope.launch(Dispatchers.IO) {
            repository.addFavorite(dbPhoto)
        }
    }

    fun deleteFavorite(dbPhoto: DBPhoto){
        GlobalScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(dbPhoto)
        }
    }
}