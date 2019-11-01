package com.example.flickrfindr.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.flickrfindr.data.PhotosRepository
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource

class MainViewModel(private val repository: PhotosRepository) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()
    private val photos = Transformations.switchMap(searchQuery) {
        repository.getPhotos(it)
    }


    init {
        searchQuery.value = "golden retriever"
    }

    fun getPhotos(): LiveData<Resource<List<Photo>>> {
        return Transformations.map(photos, ::setImageUrls)
    }

    fun getQuery(): LiveData<String> {
        return searchQuery
    }

    fun search(query: String) {
        searchQuery.value = query
    }

    private fun setImageUrls(photosResource: Resource<List<Photo>>) : Resource<List<Photo>> {
        photosResource.data?.forEach { photo ->
            photo.thumbnailUrl = String.format("https://farm%d.staticflickr.com/%s/%s_%s_m.jpg", photo.farm, photo.server, photo.id, photo.secret)
            photo.fullUrl = String.format("https://farm%d.staticflickr.com/%s/%s_%s_b.jpg", photo.farm, photo.server, photo.id, photo.secret)
        }

        return photosResource
    }
}
