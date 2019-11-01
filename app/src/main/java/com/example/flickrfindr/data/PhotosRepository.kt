package com.example.flickrfindr.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource
import com.example.flickrfindr.model.SearchPhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosRepository(private val photosService: PhotosService) {
    fun getPhotos(query: String): LiveData<Resource<List<Photo>>> {
        val liveData = MutableLiveData<Resource<List<Photo>>>()

        liveData.value = Resource.Loading()

        photosService.searchPhotos(query).enqueue(object : Callback<SearchPhotoResponse> {
            override fun onFailure(call: Call<SearchPhotoResponse>, t: Throwable) {
                liveData.value = Resource.Error(t.localizedMessage ?: "")
            }

            override fun onResponse(call: Call<SearchPhotoResponse>, response: Response<SearchPhotoResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data?.stat.equals("ok"))
                        liveData.value = Resource.Success(data?.photos?.photo ?: ArrayList())
                    else
                        liveData.value = Resource.Error(data?.message ?: "")
                }
                else
                    liveData.value = Resource.Error("")
            }

        })

        return liveData
    }
}