package com.example.imagegallery.data.favoritedata

import com.example.imagegallery.data.photodata.DBPhoto

class FavoritesRepository(private val favoritesDao : FavoritesDao) {
   // val readAllData: LiveData<List<DBPhoto>> = photoDao.readAllData()
   val readAllData: List<DBPhoto> = favoritesDao.ReadFavoritesFromDatabase()

    suspend fun deleteFavorite(dbPhoto: DBPhoto){
        favoritesDao.deleteFavorite(dbPhoto)
    }

    suspend fun addFavorite(dbPhoto : DBPhoto){
        favoritesDao.addFavorite(dbPhoto)
    }

    fun getFavorites() : List<DBPhoto> {
        return favoritesDao.ReadFavoritesFromDatabase()
    }

}