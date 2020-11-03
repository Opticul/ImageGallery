package com.example.imagegallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagegallery.data.photodata.Photo
import com.example.imagegallery.ui.favorites.FavoriteActivity
import com.example.imagegallery.ui.photos.*
import com.example.imagegallery.utilities.InjectorUtils
import com.example.imagegallery.utilities.LiveDataAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mPhotoViewModel: PhotoViewModel
    private val favoritesRepository by lazy {(applicationContext as HasAppComponent).favoritesRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun removeKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun initializeUI() {

        searchButton.setOnClickListener {
            removeKeyboard()
            GlobalScope.launch {
                mPhotoViewModel.search(searchField.text.toString())
                 }
        }

        searchField.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId != EditorInfo.IME_NULL) {
                removeKeyboard()
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
            ViewModelProvider(this, viewModelFactory).get(PhotoViewModel::class.java)
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
                    clickType.photo.localFavorite = false
                    GlobalScope.launch { favoritesRepository.deleteFavorite(clickType.photo) };
                } else {
                    clickType.photo.localFavorite = true
                    GlobalScope.launch { favoritesRepository.addFavorite(clickType.photo) };
                }

            }
            is PhotoTextHolder.ClickType.Item ->  {
                ContextCompat.startActivity(
                    clickType.viewContext,
                    Intent(clickType.viewContext, ImageDetailActivity::class.java)
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