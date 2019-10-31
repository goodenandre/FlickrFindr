package com.example.flickrfindr.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.flickrfindr.R
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource
import com.example.flickrfindr.ui.photodetail.PhotoDetailFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()
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
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerview_main.layoutManager = layoutManager

        photoAdapter = PhotoAdapter(object : PhotoClickListener {
            @Override
            override fun onClick(photo: Photo) {
                activity?.supportFragmentManager?.commit(true) {
                    var photoDetailFragment = PhotoDetailFragment.newInstance()
                    addToBackStack(PhotoDetailFragment.TAG)
                    setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    replace(R.id.container, photoDetailFragment, PhotoDetailFragment.TAG)
                }
            }

        })
        recyclerview_main.adapter = photoAdapter

        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> photoAdapter.submitList(it.data)
                is Resource.Error -> Toast.makeText(activity, "Error", Toast.LENGTH_LONG)
                //is Resource.Loading ->
            }
        })
    }
}
