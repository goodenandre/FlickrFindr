package com.example.flickrfindr.ui.photodetail;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flickrfindr.R;

public class PhotoDetailFragment extends Fragment {

    public static final String TAG = "PhotoDetailFragment";

    private PhotoDetailViewModel mViewModel;

    public static PhotoDetailFragment newInstance() {
        return new PhotoDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
