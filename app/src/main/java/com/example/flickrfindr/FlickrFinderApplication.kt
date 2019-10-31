package com.example.flickrfindr

import android.app.Application
import com.example.flickrfindr.data.PhotosRepository
import com.example.flickrfindr.data.PhotosService
import com.example.flickrfindr.data.retrofit.ServiceFactory

class FlickrFinderApplication : Application() {

    private lateinit var photosService: PhotosService
    lateinit var photosRepository: PhotosRepository

    @Override
    override fun onCreate() {
        super.onCreate()

        photosService = ServiceFactory.createService(PhotosService::class.java)
        photosRepository = PhotosRepository(photosService)
    }
}