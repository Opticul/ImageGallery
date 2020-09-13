package com.example.imagegallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView

class FavoriteActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: TheFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
    updateRecycler()
    }

    override fun onResume() {
        super.onResume()
        updateRecycler()
    }

    private fun updateRecycler(){

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        showAllButton.setOnClickListener {
            val showAllIntent = Intent(this, MainActivity::class.java)
            ContextCompat.startActivity(this, showAllIntent, null)
        }
        adapter = TheFavoriteAdapter(MainActivity.urlArray)
        recyclerView.adapter = adapter
    }
}