package com.example.imagegallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagegallery.data.photodata.DBPhoto
import com.example.imagegallery.data.photodata.DBPhotoList
import com.example.imagegallery.ui.favorites.FavoriteActivity
import com.example.imagegallery.ui.photos.PhotoViewModel
import com.example.imagegallery.utilities.TheAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    companion object FavoriteArray {
        var urlArray = arrayListOf("")
        var imageMap = mutableMapOf<String, DBPhoto>()
        var favoritesArray = mutableListOf<String>()
        var favoritesImageMap = mutableMapOf<String, DBPhoto>()
        lateinit var mPhotoViewModel: PhotoViewModel
    }
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: TheAdapter


    val retrofit = Retrofit.Builder()
        // .baseUrl("https://jsonplaceholder.typicode.com")
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myAPI = retrofit.create(ApiServicePixabayCustom::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPhotoViewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)

            var favorites = getFavorites()
            setFavorites(favorites)

        searchButton.setOnClickListener{
            GlobalScope.launch {
                search(searchField.text.toString())
            }
        }

        searchField.setOnEditorActionListener { v, actionId, event ->
            if(actionId != EditorInfo.IME_NULL){
                GlobalScope.launch {
                    search(searchField.text.toString())
                }
                true
            } else {
                false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        updateRecycler()
    }

    fun search(searchWords : String){
        println(searchWords)
        val re = Regex("[^A-Za-z0-9 ]")
        var cleanString = re.replace(searchWords, "")
        cleanString = cleanString.replace("\\s".toRegex(), "+")

        myAPI.fetchAllPhotos("18282472-6c502b3572ec282c7e32710e9",cleanString,"photo", 200).enqueue(object : Callback<DBPhotoList>{
            override fun onFailure(call: Call<DBPhotoList>, t: Throwable) {
                d("error","errorMessage: ${t.message}")
            }

            override fun onResponse(call: Call<DBPhotoList>, response: Response<DBPhotoList?>) {
                urlArray.clear()
                imageMap.clear()
                if(response.body() != null) {
                    for (photo in response.body()!!.hits) {
                        imageMap.put(photo.webformatURL, photo)
                        urlArray.add(photo.webformatURL)
                    }
                }
                println(urlArray.count())
                updateRecycler()
            }
        })
    }

    fun updateRecycler(){
        showFavorites.setOnClickListener {
            val favoriteIntent = Intent(this, FavoriteActivity::class.java)
            ContextCompat.startActivity(this, favoriteIntent, null)
        }
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = TheAdapter(urlArray)
        recyclerView.adapter = adapter
    }

    private fun getFavorites() : List<DBPhoto> {
        return mPhotoViewModel.favorites
    }

private fun setFavorites(favorites: List<DBPhoto>) {
    if (favorites.count() > 0) {
        favoritesArray.clear()
        favoritesImageMap.clear()
        for (photo in favorites) {
            favoritesArray.add(photo.webformatURL)
            favoritesImageMap[photo.webformatURL] = photo
        }
    }
}
}
