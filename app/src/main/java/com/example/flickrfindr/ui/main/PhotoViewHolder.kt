package com.example.flickrfindr.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrfindr.R
import com.example.flickrfindr.model.Photo
import com.squareup.picasso.Picasso

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var photoTitle: TextView = itemView.findViewById(R.id.photo_grid_title)
    private var photoThumbnail: ImageView = itemView.findViewById(R.id.photo_thumbnail)

    fun bind(photo: Photo) {
        photoTitle.text = photo.title

        Picasso.get()
            .load(photo.thumbnailUrl)
            .placeholder(R.drawable.photo_placeholder)
            .into(photoThumbnail)
    }
}