package com.example.imagegallery.data.photodata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagegallery.API.*
import com.example.imagegallery.data.photodata.pixabay.PixabayPhotoList
import com.example.imagegallery.data.photodata.unsplash.ImageConverter
import com.example.imagegallery.data.photodata.unsplash.UnsplashPhotoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataSource {
    private val providerList = mutableListOf<ImageProvider>()
    private val imageSources = ImageSources()
    private val providerFactory = ImageProviderFactory()

    // Singleton pattern in Kotlin
    companion object {
        @Volatile
        private var instance: DataSource? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DataSource().also { instance = it }
            }
    }

    private val photoList = mutableListOf<Photo>()
    private val photos = MutableLiveData<List<Photo>>()

    private val pixabay = imageSources.getSource(APIData.imageSourceTypes.PIXABAY)
    private val pixabayService = providerFactory.getService(pixabay) as IApiServicePixabay
    private val unsplash = imageSources.getSource(APIData.imageSourceTypes.UNSPLASH)
    private val unsplashService = providerFactory.getService(unsplash) as IApiServiceUnsplash

    init {
        providerList.add(pixabayService)
        providerList.add(unsplashService)
    }

    fun search(searchWords: String) {
        val re = Regex("[^A-Za-z0-9 ]")
        var cleanString = re.replace(searchWords, "")
        cleanString = cleanString.replace("\\s".toRegex(), "+")

//        pixabayService.fetchAllPhotos(pixabay.key,cleanString,"photo", 200).enqueue(object :

        unsplashService.fetchAllPhotos(unsplash.key, cleanString, 0, 100).enqueue(object :
            Callback<UnsplashPhotoList> {
            override fun onFailure(call: Call<UnsplashPhotoList>, t: Throwable) {
                Log.d("error", "errorMessage: ${t.message}")
            }

            override fun onResponse(
                call: Call<UnsplashPhotoList>,
                response: Response<UnsplashPhotoList>
            ) {
                photoList.clear()
                Log.d("onResponse", "Got a response")
                if (response.body() != null) {
                    Log.d("what is this response", response.toString())
                    val upl = UnsplashPhotoList(response.body()!!.results)

                    for (photo in upl.getPhotoList()) {
                        photoList.add(photo)
                        Log.d("this", photo.toString())
                    }
                } else {
                    Log.d("nope", "Response empty")
                }
                //photos.postValue(photoList)
                pixabayService.fetchAllPhotos(pixabay.key, cleanString, "photo", 200)
                    .enqueue(object :
                        Callback<PixabayPhotoList> {
                        override fun onFailure(call: Call<PixabayPhotoList>, t: Throwable) {
                            Log.d("error", "errorMessage: ${t.message}")
                        }

                        override fun onResponse(
                            call: Call<PixabayPhotoList>,
                            response: Response<PixabayPhotoList?>
                        ) {
                            Log.d("onResponse", "Got a response")
                            if (response.body() != null) {
                                val ppl = PixabayPhotoList(response.body()!!.hits)

                                for (photo in ppl.getPhotoList()) {
                                    photoList.add(photo)
                                    Log.d("this", photo.toString())
                                }
                            } else {
                                Log.d("nope", "Response empty")
                            }
                            photoList.shuffle()
                            photos.postValue(photoList)
                        }

                    })
            }
        })
        /*

*/
    }

    fun getSearchResults(): LiveData<List<Photo>> = photos
}