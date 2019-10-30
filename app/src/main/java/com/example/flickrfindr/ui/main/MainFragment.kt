package com.example.flickrfindr.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.flickrfindr.R
import com.example.flickrfindr.model.Photo
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview_main.layoutManager = layoutManager

        photoAdapter = PhotoAdapter(object : PhotoClickListener {
            @Override
            override fun onClick(photo: Photo) {

            }

        })
        recyclerview_main.adapter = photoAdapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            photoAdapter.submitList(it)
        })
    }
}
