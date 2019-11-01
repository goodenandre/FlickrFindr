package com.example.flickrfindr.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.flickrfindr.R
import com.example.flickrfindr.model.Photo

class PhotoAdapter(private val photoClickListener: PhotoClickListener) : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback()) {
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_grid_item, parent, false)
        return PhotoViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), photoClickListener)
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}