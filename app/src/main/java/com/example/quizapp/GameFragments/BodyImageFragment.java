package com.example.quizapp.GameFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.quizapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BodyImageFragment extends Fragment {

    ImageView imageView;
    Dialog dialog;

    public BodyImageFragment() {
        // Required empty public constructor
    }

    public static BodyImageFragment newInstance() {
        BodyImageFragment fragment = new BodyImageFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = getView().findViewById(R.id.imageQuizBody);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testpopResizeImage();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body_image, container, false);
    }

    public void testpopResizeImage() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_body_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}