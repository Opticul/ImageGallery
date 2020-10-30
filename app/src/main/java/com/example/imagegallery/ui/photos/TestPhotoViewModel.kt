package com.example.imagegallery.ui.photos

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagegallery.data.favoritedata.FavoritesRepository
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.data.photodata.PhotoRepository

class TestPhotoViewModel(private val photoRepository: PhotoRepository) : ViewModel() {

    fun getSearchResults() = photoRepository.getSearchResults()

    fun search(searchWord : String) = photoRepository.search(searchWord)

}