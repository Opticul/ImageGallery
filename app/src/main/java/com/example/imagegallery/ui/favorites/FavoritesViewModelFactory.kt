package com.example.imagegallery.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagegallery.data.favoritedata.FavoritesRepository

class FavoritesViewModelFactory( private val favoritesRepository: FavoritesRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(favoritesRepository) as T
    }
}