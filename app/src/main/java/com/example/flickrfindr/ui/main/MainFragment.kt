package com.example.flickrfindr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.flickrfindr.R
import com.example.flickrfindr.model.Photo
import com.example.flickrfindr.model.Resource
import com.example.flickrfindr.ui.photodetail.PhotoDetailFragment
import com.example.flickrfindr.utils.hideKeyboard
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.network_error.*

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

        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?) = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.search(it) }
                hideKeyboard(activity)
                return true
            }
        })

        textview_error_retry.setOnClickListener { viewModel.search(search_view.query.toString()) }

        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerview_main.layoutManager = layoutManager

        photoAdapter = PhotoAdapter(object : PhotoClickListener {
            @Override
            override fun onClick(photo: Photo) {
                val ft = activity?.supportFragmentManager?.beginTransaction()
                ft?.let {
                    val photoDetailFragment = PhotoDetailFragment.newInstance(photo.fullUrl)
                    photoDetailFragment.show(it, PhotoDetailFragment.TAG)
                }
            }

        })
        recyclerview_main.adapter = photoAdapter

        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    progress_bar.visibility = View.GONE
                    network_error.visibility = View.GONE

                    if (it.data?.size == 0) {
                        no_results.visibility = View.VISIBLE
                        recyclerview_main.visibility = View.GONE
                    } else {
                        no_results.visibility = View.GONE
                        recyclerview_main.visibility = View.VISIBLE
                        photoAdapter.submitList(it.data)
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.GONE
                    recyclerview_main.visibility = View.GONE
                    no_results.visibility = View.GONE
                    network_error.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    recyclerview_main.visibility = View.GONE
                    no_results.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                    network_error.visibility = View.GONE
                }
            }
        })
    }
}
