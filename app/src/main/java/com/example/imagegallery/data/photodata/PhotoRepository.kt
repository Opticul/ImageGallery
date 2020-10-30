package com.example.imagegallery.data.photodata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


class PhotoRepository() {

    private val photoDao = PhotoDao.getInstance()

    // region implements imageChecks
    // Consider searching towards pixabay for a single item instead of storing here, scales better
    private val defaultPhoto = Photo(0, "No User", 0, 0, 0, 0, 0, "", "",false)
    private var photos = mutableMapOf<Int, Photo>()

    fun getImageById(imageId: Int): Photo {
        setPhotos()
        return photos.getOrElse(imageId, { defaultPhoto })
    }

    private fun setPhotos() {
        photos.clear()
        photos = convertToMap(photoDao.getSearchResults().value ?: emptyList())
    }

    private fun convertToMap(inList: List<Photo>): MutableMap<Int, Photo> {
        val nowMap = mutableMapOf<Int, Photo>()
        for (photo in inList) {
            nowMap[photo.id] = photo
        }
        return nowMap
    }

    // endregion implements imageChecks

    fun search(searchWords: String) {
        photoDao.search(searchWords)
    }

    fun getSearchResults() = photoDao.getSearchResults()
    }
