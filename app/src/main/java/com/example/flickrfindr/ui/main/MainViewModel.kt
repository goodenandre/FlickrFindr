package com.example.flickrfindr.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.flickrfindr.FlickrFinderApplication
import com.example.flickrfindr.data.PhotosRepository
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val searchQuery = MutableLiveData<String>()
    private var repository: PhotosRepository = (app as FlickrFinderApplication).photosRepository
    private val photos = Transformations.switchMap(searchQuery) {
        repository.getPhotos(it)
    }


    init {
        searchQuery.value = "golden retriever"
    }

    fun getPhotos(): LiveData<Resource<List<Photo>>> {
        return Transformations.map(photos, ::setImageUrls)
    }

    private fun setImageUrls(photosResource: Resource<List<Photo>>) : Resource<List<Photo>> {
        photosResource.data?.forEach { photo ->
            photo.thumbnailUrl = String.format("https://farm%d.staticflickr.com/%s/%s_%s_m.jpg", photo.farm, photo.server, photo.id, photo.secret)
            photo.fullUrl = String.format("https://farm%d.staticflickr.com/%s/%s_%s_o.jpg", photo.farm, photo.server, photo.id, photo.secret)
        }

        return photosResource
    }

    fun search(query: String) {
        searchQuery.value = query
    }
}
