package com.example.imagegallery.data.favoritedata

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imagegallery.data.photodata.Photo

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(dbphoto : Photo)

@Delete
suspend fun deleteFavorite(dbphoto: Photo)

@Query("SELECT * FROM photo_data ORDER BY id ASC")
fun readFavoritesFromDatabase(): List<Photo>//LiveData<List<DBPhoto>>

    @Query("SELECT * FROM photo_data WHERE id=:id ")
    fun getFavoriteByID(id: String): Photo
}


