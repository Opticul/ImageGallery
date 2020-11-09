package com.example.imagegallery.data.photodata


class PhotoRepository() {

    private val dataSource = DataSource.getInstance()

    // region implements imageChecks
    // Consider searching towards pixabay for a single item instead of storing here, scales better
    private val defaultPhoto = Photo("","", "No User", 0, "", "", "",  false,"Unknown")
    private var photos = mutableMapOf<String, Photo>()

    fun getImageById(imageId: String): Photo {
        setPhotos()
        return photos.getOrElse(imageId, { defaultPhoto })
    }

    private fun setPhotos() {
        photos.clear()
        photos = convertToMap(dataSource.getSearchResults().value ?: emptyList())
    }

    private fun convertToMap(inList: List<Photo>): MutableMap<String, Photo> {
        val nowMap = mutableMapOf<String, Photo>()
        for (photo in inList) {
            nowMap[photo.id] = photo
        }
        return nowMap
    }

    // endregion implements imageChecks

    fun search(searchWords: String) {
        dataSource.search(searchWords)
    }

    fun getSearchResults() = dataSource.getSearchResults()
    }
