package com.example.quizapp.GameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;

public class TrueFalseFragment extends Fragment {

    public TrueFalseFragment() {
        // Required empty public constructor
    }

    public static TrueFalseFragment newInstance() {
        TrueFalseFragment fragment = new TrueFalseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_true_false, container, false);
    }
}