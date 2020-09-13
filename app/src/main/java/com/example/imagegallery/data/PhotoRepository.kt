package com.example.imagegallery.data

import androidx.lifecycle.LiveData

class PhotoRepository(private val photoDao : PhotoDao) {
   // val readAllData: LiveData<List<DBPhoto>> = photoDao.readAllData()
   val readAllData: List<DBPhoto> = photoDao.readAllData()

    suspend fun deletePhoto(dbPhoto: DBPhoto){
        photoDao.deletePhoto(dbPhoto)
    }

    suspend fun addPhoto(dbPhoto : DBPhoto){
        photoDao.addPhoto(dbPhoto)
    }
}