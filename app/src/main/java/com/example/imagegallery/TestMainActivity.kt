package com.example.imagegallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.ui.favorites.FavoriteActivity
import com.example.imagegallery.ui.favorites.FavoriteTextHolder
import com.example.imagegallery.ui.photos.*
import com.example.imagegallery.utilities.InjectorUtils
import com.example.imagegallery.utilities.LiveDataAdapter
import com.example.imagegallery.utilities.TheAdapter
import com.example.imagegallery.utilities.TheFavoriteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_as_part_of_list.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TestMainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mPhotoViewModel: TestPhotoViewModel
    private val favoritesRepository by lazy {(applicationContext as HasAppComponent).favoritesRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {

        searchButton.setOnClickListener {
            GlobalScope.launch {
                mPhotoViewModel.search(searchField.text.toString())
            }
        }

        searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId != EditorInfo.IME_NULL) {
                GlobalScope.launch {
                    mPhotoViewModel.search(searchField.text.toString())
                }
                true
            } else {
                false
            }
        }

        showFavorites.setOnClickListener {
            val favoriteIntent = Intent(this, FavoriteActivity::class.java)
            ContextCompat.startActivity(this, favoriteIntent, null)
        }

        val viewModelFactory = InjectorUtils.providePhotosViewModelFactory(this)
        mPhotoViewModel =
            ViewModelProvider(this, viewModelFactory).get(TestPhotoViewModel::class.java)
        mPhotoViewModel.getSearchResults()
            .observe(this,
                {
                    updateRecycler()
        })
        //updateRecycler()
    }

    override fun onResume() {
        super.onResume()
        updateRecycler()
    }

    private val clickTypeListener : (PhotoTextHolder.ClickType) -> Unit = { clickType: PhotoTextHolder.ClickType ->
        when (clickType) {
            is PhotoTextHolder.ClickType.ChangeFavoriteStatus -> {
                if (favoritesRepository.isFavorite(clickType.photo.id)) {
                    GlobalScope.launch { favoritesRepository.deleteFavorite(clickType.photo) };
                } else {
                    GlobalScope.launch { favoritesRepository.addFavorite(clickType.photo) };
                }

            }
            is PhotoTextHolder.ClickType.Item ->  {
                ContextCompat.startActivity(
                    clickType.viewContext,
                    Intent(clickType.viewContext, TestImageDetailActivity::class.java)
                        .putExtra("id", clickType.itemId),
                    null
                )
            }
        }
    }

    private fun addFavoriteStatus(inList : LiveData<List<Photo>>) : LiveData<List<Photo>> {

        for (photo in inList.value ?: emptyList()) {
            photo.localFavorite = favoritesRepository.isFavorite(photo.id)
        }
        return inList
    }

    private fun updateRecycler(){
        Log.d("...","UpdateRecycler Called")

        //TODO Move adapter creation out of update

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = LiveDataAdapter(addFavoriteStatus(mPhotoViewModel.getSearchResults()),clickTypeListener)
        //adapter = LiveDataAdapter(mPhotoViewModel.getSearchResults())
        //recyclerView.adapter = adapter
    }

}