package com.example.imagegallery.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import com.example.imagegallery.data.photodata.PhotoRepository

class PhotoViewModelFactory(private val photoRepository : PhotoRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TestPhotoViewModel(photoRepository) as T
    }
}