package com.example.imagegallery.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPhoto(dbphoto : DBPhoto)

@Delete
suspend fun deletePhoto(dbphoto: DBPhoto)

@Query("SELECT * FROM photo_data ORDER BY id ASC")
fun readAllData(): List<DBPhoto>//LiveData<List<DBPhoto>>

}