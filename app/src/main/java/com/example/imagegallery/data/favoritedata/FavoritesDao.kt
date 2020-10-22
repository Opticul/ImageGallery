package com.example.imagegallery.data.favoritedata

import androidx.room.*
import com.example.imagegallery.data.photodata.DBPhoto

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(dbphoto : DBPhoto)

@Delete
suspend fun deleteFavorite(dbphoto: DBPhoto)

@Query("SELECT * FROM photo_data ORDER BY id ASC")
fun ReadFavoritesFromDatabase(): List<DBPhoto>//LiveData<List<DBPhoto>>

}