package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.quizapp.GameFragments.BodyImageFragment;
import com.example.quizapp.GameFragments.BodyPopupImageFragment;
import com.example.quizapp.GameFragments.FourAnswersFragment;
import com.example.quizapp.GameFragments.SixAnswersFragment;

public class Quiz_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
        replaceFragment(new SixAnswersFragment(), R.id.quizLargeFrameLayout);
        replaceFragment(new BodyPopupImageFragment(), R.id.bodyFrameLayout);

    }

    //==============================================================================================

    private void replaceFragment(Fragment fragment, int frameLayout) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.commit();
    }

}