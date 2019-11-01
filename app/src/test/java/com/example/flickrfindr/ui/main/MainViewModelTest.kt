package com.example.flickrfindr.ui.main

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.flickrfindr.FlickrFinderApplication
import com.example.flickrfindr.data.PhotosRepository
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.atLeastOnce
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {
    private lateinit var viewModel : MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var photosRepository: PhotosRepository
    @Mock
    private lateinit var queryObserver: Observer<String>
    @Mock
    private lateinit var photoObserver: Observer<Resource<List<Photo>>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(photosRepository)
        viewModel.getQuery().observeForever(queryObserver)
        viewModel.getPhotos().observeForever(photoObserver)
    }

    @Test
    fun search_updatesLiveData() {
        val query = "test"

        viewModel.search(query)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)
        argumentCaptor.run {
            Mockito.verify(queryObserver, atLeastOnce()).onChanged(capture())
            assertEquals(query, value)
        }
    }

    @Test
    fun search_callsGetPhotos() {
        val query = "test"

        val liveData = MutableLiveData<Resource<List<Photo>>>()
        val resource = Resource.Success(listOf(
            Photo("1", "me", "secret", "server1", 12, "title", 1, 0, 0)
        ))
        liveData.value = resource

        Mockito.`when`(photosRepository.getPhotos(query)).thenReturn(liveData)

        viewModel.search(query)

        val argumentCaptor = argumentCaptor<Resource<List<Photo>>>()
        argumentCaptor.run {
            Mockito.verify(photoObserver, atLeastOnce()).onChanged(capture())
            assertEquals(resource, firstValue)
        }
    }

    @Test
    fun getPhotos_MapsImageUrls() {
        val query = "test"

        val liveData = MutableLiveData<Resource<List<Photo>>>()
        val resource = Resource.Success(listOf(
            Photo("1", "me", "secret", "server1", 12, "title", 1, 0, 0)
        ))
        liveData.value = resource

        Mockito.`when`(photosRepository.getPhotos(query)).thenReturn(liveData)

        viewModel.search(query)

        val argumentCaptor = argumentCaptor<Resource<List<Photo>>>()
        argumentCaptor.run {
            Mockito.verify(photoObserver, atLeastOnce()).onChanged(capture())
            assertEquals(resource.data?.get(0), firstValue.data?.get(0))
            assertEquals("https://farm12.staticflickr.com/server1/1_secret_m.jpg", firstValue.data?.get(0)?.thumbnailUrl)
            assertEquals("https://farm12.staticflickr.com/server1/1_secret_b.jpg", firstValue.data?.get(0)?.fullUrl)
        }
    }
}