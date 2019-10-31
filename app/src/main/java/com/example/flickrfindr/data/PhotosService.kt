package com.example.flickrfindr.data

import com.example.flickrfindr.model.SearchPhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {
    @GET("?method=flickr.photos.search&per_page=25")
    fun searchPhotos(@Query("text") text: String) : Call<SearchPhotoResponse>
}