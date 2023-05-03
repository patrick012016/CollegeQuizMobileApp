package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;
import static com.example.quizapp.Utils.Constans.arrayResultColors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.GameFragments.TrueFalseFragment;
import com.example.quizapp.dto.ResultDto;
import com.example.quizapp.hubs.HubConnectivity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
//    List<ResultDto> listResult;


    CardView userCard;
    CardView resultCard;
    CardView leaderCard;

    TextView userText;
    TextView userResultText;
    TextView userLaderText;
    LinearLayout linearLayoutResult;
    ResultDto[] arrayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        linearLayoutResult = findViewById(R.id.linearLayoutResult);
        Intent intent = getIntent();
        arrayResult = (ResultDto[]) intent.getSerializableExtra("arrayResult");
        setResultView(Arrays.stream(arrayResult).count());
    }

    public void setResultView(long countUser) {
        for (int i = 0; i < countUser-1; i++) {
            View viewResultView = getLayoutInflater().inflate(R.layout.result_view_card, null);
            userCard = viewResultView.getRootView().findViewById(R.id.resultUserCard);
            resultCard = viewResultView.getRootView().findViewById(R.id.resultScoreCard);
            userText = viewResultView.getRootView().findViewById(R.id.textUser);
            userResultText = viewResultView.getRootView().findViewById(R.id.textResult);

            userText.setText(arrayResult[i].getUsername());
            userResultText.setText(String.valueOf(arrayResult[i].getScore()) + " " + "(+" +
                    String.valueOf(arrayResult[i].getNewPoints()) + ")");

            userCard.setCardBackgroundColor(Color.parseColor(arrayResultColors[i]));
            linearLayoutResult.addView(viewResultView);
        }
        if(arrayResult[(int) Arrays.stream(arrayResult).count()-1].getCurrentStreak() != 0) {
            View liderResultView = getLayoutInflater().inflate(R.layout.result_leader_view_card, null);
            userLaderText = liderResultView.getRootView().findViewById(R.id.textLeader);
            userLaderText.setText(String.valueOf(arrayResult[(int) Arrays.stream(arrayResult).count() - 1].getUsername())
                    + ": " + arrayResult[(int) Arrays.stream(arrayResult).count() - 1].getCurrentStreak());
            linearLayoutResult.addView(liderResultView);
        }


        hubConnectivity.onGame(message -> {
            finish();
        });

        hubConnectivity.onDisconnect(message -> {
            runOnUiThread(() -> Toast.makeText(ResultActivity.this,
                    "Rozłączono z quizem", Toast.LENGTH_LONG).show());
            finish();
        });

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

    }
}