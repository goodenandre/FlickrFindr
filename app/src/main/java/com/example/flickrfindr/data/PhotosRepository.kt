package com.example.flickrfindr.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.flickrfindr.model.Photo

class PhotosRepository {
    fun getPhotos(): LiveData<List<Photo>> {
        var liveData = MediatorLiveData<List<Photo>>()
        var list = mutableListOf<Photo>()
        list.add(Photo("", "Photo 1"))
        list.add(Photo("", "Photo 2"))
        list.add(Photo("", "Photo 3"))
        list.add(Photo("", "Photo 4"))
        list.add(Photo("", "Photo 5"))
        liveData.value = list

        return liveData
    }
}