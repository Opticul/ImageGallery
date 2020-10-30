package com.example.imagegallery.data.favoritedata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagegallery.data.photodata.Photo

@Database(entities = [Photo::class], version = 5, exportSchema = false)
abstract class FavoritesDB : RoomDatabase() {

    abstract fun favoritesDao() : FavoritesDao

    //Singleton database pattern
    companion object{
        @Volatile
        private var INSTANCE : FavoritesDB? = null

        fun getDatabase(context : Context): FavoritesDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            //Protected from being run by several threads
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDB::class.java,
                    "photo_data"
                ).fallbackToDestructiveMigration(). allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}