package com.example.flickrfindr.ui.main

import com.example.flickrfindr.model.Photo

interface PhotoClickListener {
    fun onClick(photo: Photo)
}