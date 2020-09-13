package com.example.imagegallery.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*


class PhotoViewModel(application: Application) : AndroidViewModel(application){

   // val readAllData: LiveData<List<DBPhoto>>
   val readAllData: List<DBPhoto>

    private val repository : PhotoRepository

    init {
        var photoDao = PhotoDatabase.getDatabase((application)).photoDao()
        repository = PhotoRepository(photoDao)
        readAllData = repository.readAllData
    }

    fun addPhoto(dbPhoto: DBPhoto){
        GlobalScope.launch(Dispatchers.IO) {
            repository.addPhoto(dbPhoto)
        }
    }

    fun deletePhoto(dbPhoto: DBPhoto){
        GlobalScope.launch(Dispatchers.IO) {
            repository.deletePhoto(dbPhoto)
        }
    }
}