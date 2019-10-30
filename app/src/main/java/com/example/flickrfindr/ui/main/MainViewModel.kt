package com.example.flickrfindr.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.flickrfindr.FlickrFinderApplication
import com.example.flickrfindr.data.PhotosRepository
import com.example.flickrfindr.model.Photo

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    // TODO: Implement the ViewModel
    private var repository: PhotosRepository = PhotosRepository()
    private val photos = MediatorLiveData<List<Photo>>()

    init {
        val photosLiveData = repository.getPhotos()
        photos.apply {
            addSource(photosLiveData) {
                value = it
            }
        }
    }

    fun getPhotos(): LiveData<List<Photo>> {
        return photos
    }
}
