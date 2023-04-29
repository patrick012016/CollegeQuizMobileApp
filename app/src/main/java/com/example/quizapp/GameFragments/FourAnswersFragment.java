package com.example.quizapp.GameFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;


public class FourAnswersFragment extends Fragment {

    CardView cardA, cardB, cardC, cardD;

    public FourAnswersFragment() {
        // Required empty public constructor
    }

    public static FourAnswersFragment newInstance() {
        FourAnswersFragment fragment = new FourAnswersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardA = getView().findViewById(R.id.cardA);
        cardB = getView().findViewById(R.id.cardB);
        cardC = getView().findViewById(R.id.cardC);
        cardD = getView().findViewById(R.id.cardD);

        cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardA.setScaleX(0.85f);
                cardA.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardB.setScaleX(0.85f);
                cardB.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardC.setScaleX(0.85f);
                cardC.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardD.setScaleX(0.85f);
                cardD.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four_answers, container, false);
    }

    public void blockCardResult (CardView one, CardView two, CardView three, CardView four)
    {
        one.setFocusable(false);
        one.setClickable(false);
        two.setFocusable(false);
        two.setClickable(false);
        three.setFocusable(false);
        three.setClickable(false);
        four.setFocusable(false);
        four.setClickable(false);
    }
}