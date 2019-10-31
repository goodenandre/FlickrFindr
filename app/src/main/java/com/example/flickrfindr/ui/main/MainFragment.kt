package com.example.flickrfindr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
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

        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.search(query)
                }

                return true
            }

        })

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
                /*activity?.supportFragmentManager?.commit(true) {
                    val photoDetailFragment = PhotoDetailFragment.newInstance(photo.fullUrl)
                    addToBackStack(PhotoDetailFragment.TAG)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    replace(R.id.container, photoDetailFragment, PhotoDetailFragment.TAG)
                }*/
            }

        })
        recyclerview_main.adapter = photoAdapter

        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    progress_bar.visibility = View.GONE
                    recyclerview_main.visibility = View.VISIBLE
                    photoAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.GONE
                    recyclerview_main.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    recyclerview_main.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }
}
