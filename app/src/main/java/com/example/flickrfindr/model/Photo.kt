package com.example.flickrfindr.model

data class Photo(val id: String,
                 val owner: String,
                 val secret: String,
                 val server: String,
                 val farm: Int,
                 val title: String,
                 val ispublic: Int,
                 val isfriend: Int,
                 val isfamily: Int,
                 var thumbnailUrl : String = "",
                 var fullUrl: String = "")