package com.example.imagegallery.ui.favorites

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.data.photodata.PhotoRepository

class FavoriteViewModel(private val favoritesRepository: FavoritesRepository) : ViewModel() {

    fun isFavorite(id : String) = favoritesRepository.isFavorite(id)

    fun getFavorites() = favoritesRepository.getFavorites()

    fun getFavoritesAsLiveData() = favoritesRepository.getFavoritesAsLiveData()


    suspend fun removeFavorite(photo : Photo) = favoritesRepository.deleteFavorite(photo)

    suspend fun removeFavoriteByID(id : String) = favoritesRepository.deleteFavoriteByID(id)

}