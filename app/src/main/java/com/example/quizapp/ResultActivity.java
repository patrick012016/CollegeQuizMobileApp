package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quizapp.hubs.HubConnectivity;

public class ResultActivity extends AppCompatActivity {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }



}