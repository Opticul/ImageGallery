package com.example.imagegallery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [DBPhoto::class], version = 3, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao() : PhotoDao

    //Singleton database pattern
    companion object{
        @Volatile
        private var INSTANCE : PhotoDatabase? = null

        fun getDatabase(context : Context): PhotoDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            //Protected from being run by several threads
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoDatabase::class.java,
                    "photo_data"
                ).fallbackToDestructiveMigration(). allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}