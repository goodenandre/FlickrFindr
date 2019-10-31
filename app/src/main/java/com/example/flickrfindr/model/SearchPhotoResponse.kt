package com.example.flickrfindr.model

class SearchPhotoResponse(stat: String,
                          val photos: Photos?
) : BaseResponse(stat)

class Photos(val photo: List<Photo>,
             val page: Int,
             val pages: String,
             val perpage: Int,
             val total: String)