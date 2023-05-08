package pl.dominikpiskor.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.dominikpiskor.quizapp.dto.ResultDto;
import pl.dominikpiskor.quizapp.hubs.HubConnectivity;

import java.util.Arrays;

import pl.dominikpiskor.quizapp.Utils.Constans;

public class ResultActivity extends AppCompatActivity {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(Constans.HUBURL);

    CardView userCard;
    CardView resultCard;
    CardView leaderCard;

    TextView userText;
    TextView userResultText;
    TextView userLaderText;
    LinearLayout linearLayoutResult;
    ResultDto[] arrayResult;

    @Override
    public void onBackPressed() { }

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

            userCard.setCardBackgroundColor(Color.parseColor(Constans.arrayResultColors[i]));
            linearLayoutResult.addView(viewResultView);
        }
        if(arrayResult[(int) Arrays.stream(arrayResult).count()-1].getCurrentStreak() != 0) {
            View leaderResultView = getLayoutInflater().inflate(R.layout.result_leader_view_card, null);
            userLaderText = leaderResultView.getRootView().findViewById(R.id.textLeader);
            leaderCard = leaderResultView.getRootView().findViewById(R.id.leaderUser);

            ImageView imageView1 = leaderResultView.findViewById(R.id.iconImageOne);
            ImageView imageView2 = leaderResultView.findViewById(R.id.iconImageTwo);

            ObjectAnimator scaleDown1 = ObjectAnimator.ofPropertyValuesHolder(
                    imageView1,
                    PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.2f));
            scaleDown1.setDuration(1000);
            scaleDown1.setRepeatCount(ObjectAnimator.INFINITE);
            scaleDown1.setRepeatMode(ObjectAnimator.REVERSE);
            scaleDown1.start();
            ObjectAnimator scaleDown2 = ObjectAnimator.ofPropertyValuesHolder(
                    imageView2,
                    PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.2f));
            scaleDown2.setDuration(1000);
            scaleDown2.setRepeatCount(ObjectAnimator.INFINITE);
            scaleDown2.setRepeatMode(ObjectAnimator.REVERSE);
            scaleDown2.start();

            userLaderText.setText(String.valueOf(arrayResult[(int) Arrays.stream(arrayResult).count() - 1].getUsername())
                    + ": " + arrayResult[(int) Arrays.stream(arrayResult).count() - 1].getCurrentStreak());
            linearLayoutResult.addView(leaderResultView);
        }


        hubConnectivity.onGame(message -> {
            finish();
        });

        hubConnectivity.onDisconnect(message -> {
            runOnUiThread(() -> Toast.makeText(ResultActivity.this,
                    "Rozłączono z quizem", Toast.LENGTH_SHORT).show());
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