package com.example.imagegallery.ui.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagegallery.R
import com.example.imagegallery.MainActivity
import com.example.imagegallery.utilities.InjectorUtils
import com.example.imagegallery.ui.favorites.FavoriteTextHolder.ClickType.*
import com.example.imagegallery.ui.photos.ImageDetailActivity
import com.example.imagegallery.utilities.TheFavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var thisClickListener: (FavoriteTextHolder.ClickType) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initializeUI()
    }

    private fun initializeUI() {

        showAllButton.setOnClickListener {
            val showAllIntent = Intent(this, MainActivity::class.java)
            ContextCompat.startActivity(this, showAllIntent, null)
        }

        setClickListener()

        val viewModelFactory = InjectorUtils.provideFavoritesViewModelFactory(this)
        mFavoriteViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        mFavoriteViewModel.getFavoritesAsLiveData()
            .observe(this, { inList -> Log.d("Favorites", inList.toString()); updateRecycler() })
    }

    override fun onResume() {
        super.onResume()
        updateRecycler()
    }

    private fun setClickListener() {
        thisClickListener = { clickType: FavoriteTextHolder.ClickType ->
            when (clickType) {
                is Favourites -> GlobalScope.launch {
                    mFavoriteViewModel.removeFavoriteByID(
                        clickType.itemId
                    )
                }
                is Item -> ContextCompat.startActivity(
                    clickType.viewContext,
                    Intent(clickType.viewContext, ImageDetailActivity::class.java)
                        .putExtra("id", clickType.itemId),
                    null
                )
            }
        }
    }

    fun updateRecycler() {
        // TODO Change to sending in list instead of replacing adapter


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            TheFavoriteAdapter(mFavoriteViewModel.getFavoritesAsLiveData(), thisClickListener)
    }
}