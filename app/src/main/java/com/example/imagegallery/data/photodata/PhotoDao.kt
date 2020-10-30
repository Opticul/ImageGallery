package com.example.imagegallery.data.photodata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagegallery.ApiServicePixabayCustom
import com.example.imagegallery.MainActivity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class PhotoDao {

    // Singleton pattern in Kotlin
    companion object{
        @Volatile private var instance: PhotoDao? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PhotoDao().also{ instance = it}
            }
    }

    private val photoList = mutableListOf<Photo>()
    private val photos = MutableLiveData<List<Photo>>()

    val retrofit = Retrofit.Builder()
        // .baseUrl("https://jsonplaceholder.typicode.com")
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myAPI = retrofit.create(ApiServicePixabayCustom::class.java)

    fun search(searchWords : String){
       Log.d("Observing", searchWords)
        val re = Regex("[^A-Za-z0-9 ]")
        var cleanString = re.replace(searchWords, "")
        cleanString = cleanString.replace("\\s".toRegex(), "+")

        myAPI.fetchAllPhotos("18282472-6c502b3572ec282c7e32710e9",cleanString,"photo", 200).enqueue(object :
            Callback<PhotoList> {
            override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                Log.d("error", "errorMessage: ${t.message}")
            }

            override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList?>) {
                Log.d("onResponse","Got a response")
             photoList.clear()

                if(response.body() != null) {

                     for (photo in response.body()!!.hits) {
                         photoList.add(photo)
                    }
                }
                photos.postValue(photoList)
            }
        })

    }

    fun getSearchResults() : LiveData<List<Photo>> = photos
}