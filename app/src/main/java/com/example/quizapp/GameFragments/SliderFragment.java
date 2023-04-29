package com.example.quizapp.GameFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

public class SliderFragment extends Fragment {

    RangeSlider rangeSlider;
    public SliderFragment() {
        // Required empty public constructor
    }


    public static SliderFragment newInstance() {
        SliderFragment fragment = new SliderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rangeSlider = getView().findViewById(R.id.discreteRangeSlider);
        rangeSlider.setValues(30f, 90.0f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slider, container, false);
    }
}