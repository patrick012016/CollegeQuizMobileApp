package com.example.quizapp.GameFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.MenuActivity;
import com.example.quizapp.Quiz_Activity;
import com.example.quizapp.R;

public class BodyPopupImageFragment extends Fragment {

    AppCompatButton appCompatButton;
    Dialog dialog;

    public BodyPopupImageFragment() {
        // Required empty public constructor
    }

    public static BodyPopupImageFragment newInstance() {
        BodyPopupImageFragment fragment = new BodyPopupImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appCompatButton = getActivity().findViewById(R.id.buttonPopImage);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testpop();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body_popup_image, container, false);
    }

    public void testpop() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_body_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}