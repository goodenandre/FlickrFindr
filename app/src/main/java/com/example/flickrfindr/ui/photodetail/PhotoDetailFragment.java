package com.example.flickrfindr.ui.photodetail;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.flickrfindr.R;
import com.squareup.picasso.Picasso;

public class PhotoDetailFragment extends DialogFragment {

    public static final String TAG = "PhotoDetailFragment";
    private static final String ARGS_IMAGE_URL = "ImageUrl";

    private ImageView mFullPhotoImageView;

    public static PhotoDetailFragment newInstance(String imageUrl) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_detail_fragment, container, false);

        mFullPhotoImageView = view.findViewById(R.id.photo_full_imageview);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            Picasso.get()
                    .load(getArguments().getString(ARGS_IMAGE_URL))
                    .noPlaceholder()
                    .into(mFullPhotoImageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null && dialog.getWindow() != null)
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
