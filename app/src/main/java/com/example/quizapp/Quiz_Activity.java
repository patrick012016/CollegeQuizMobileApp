package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

public class Quiz_Activity extends AppCompatActivity {

    CardView cardA, cardB, cardC, cardD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        cardA = findViewById(R.id.cardA);
        cardB = findViewById(R.id.cardB);
        cardC = findViewById(R.id.cardC);
        cardD = findViewById(R.id.cardD);

        cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardA.setScaleX(0.85f);
                cardA.setScaleY(0.85f);
            }
        });
    }
}