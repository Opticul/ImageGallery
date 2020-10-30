package com.example.imagegallery.data.favoritedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagegallery.data.photodata.Photo

class FavoritesRepository(private val favoritesDao : FavoritesDao) {

    private val favoritesList = mutableListOf<Photo>()
    private val favorites = MutableLiveData<List<Photo>>()
    private val defaultPhoto = Photo(0, "No User", 0, 0, 0, 0, 0, "", "",false)

    init {
        updateFavorites()
    }
    suspend fun deleteFavorite(photo: Photo){
        favoritesDao.deleteFavorite(photo)
        updateFavorites()
    }

    suspend fun deleteFavoriteByID(photo: Int){
        val favorites = getFavorites()
        for (photos in favorites){
            if (photos.id == photo) {
                deleteFavorite(photos)
                break;
            }
        }
    }

    suspend fun addFavorite(photo : Photo){
        favoritesDao.addFavorite(photo)
        updateFavorites()
    }

    suspend fun changeFavorite(photo : Photo){
        if (isFavorite(photo.id)){
            deleteFavorite(photo)
        } else {
            addFavorite(photo)
        }
        updateFavorites()
    }

    fun getFavoriteByID(id : Int) : Photo {
        if (id == 0) {
            return defaultPhoto
        }
        return favoritesDao.getFavoriteByID(id)
    }

    fun isFavorite(id : Int) : Boolean {
        return convertToMap(getFavorites()).containsKey(id)
    }

    private fun convertToMap(inList : List<Photo>) : MutableMap<Int,Photo> {
        val nowMap = mutableMapOf<Int,Photo>()
        for (photo in inList) {
            nowMap[photo.id] = photo
        }
        return nowMap
    }

    fun getFavorites() : List<Photo> {
        return favoritesDao.readFavoritesFromDatabase()
    }

    private fun updateFavorites() {
        favoritesList.clear()

        for (photo in favoritesDao.readFavoritesFromDatabase()){
            favoritesList.add(photo)
        }
        favorites.postValue(favoritesList)
    }

    fun getFavoritesAsLiveData() : LiveData<List<Photo>> {
        return favorites
    }

}