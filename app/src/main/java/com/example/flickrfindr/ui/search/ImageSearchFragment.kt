package com.example.flickrfindr.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flickrfindr.R

class ImageSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ImageSearchFragment()
    }

    private lateinit var viewModel: ImageSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ImageSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
