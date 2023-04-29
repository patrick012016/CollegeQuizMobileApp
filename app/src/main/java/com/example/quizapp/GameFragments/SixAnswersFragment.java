package com.example.quizapp.GameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;

public class SixAnswersFragment extends Fragment {

    public SixAnswersFragment() {
        // Required empty public constructor
    }

    public static SixAnswersFragment newInstance() {
        SixAnswersFragment fragment = new SixAnswersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_six_answers, container, false);
    }
}