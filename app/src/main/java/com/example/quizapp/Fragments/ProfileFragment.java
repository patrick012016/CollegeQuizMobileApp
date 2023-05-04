package com.example.quizapp.Fragments;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizapp.R;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
//    public void addvieeee() {
//        for (int i = 0; i < 2; i++) {
//            View viewNew = getLayoutInflater().inflate(R.layout.result_view_card, null);
//            cardView = viewNew.getRootView().findViewById(R.id.resultUserCard);
//
//            textView = viewNew.getRootView().findViewById(R.id.textResult);
//            textView1 = viewNew.getRootView().findViewById(R.id.textUser);
//
//
//            textView.setText(array1[i]);
//            textView1.setText(array1[i]);
//
//
//        //    cardView.setCardBackgroundColor(Color.parseColor(array[i]));
//            linearLayout.addView(viewNew);
//        }
//        View lider = getLayoutInflater().inflate(R.layout.result_leader_view_card, null);
//        textView2 = lider.getRootView().findViewById(R.id.textLeader);
//        textView2.setText("Nikola");
//        linearLayout.addView(lider);
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 200000);
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float value = (float) animator.getAnimatedValue();
//                animator.setDuration(10000);
//                textView2.setText(valueAnimator.getAnimatedValue().toString());
//            }
//        });
//        animator.start();
//    }
}