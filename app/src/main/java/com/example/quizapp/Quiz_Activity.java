package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.GameFragments.BodyImageFragment;
import com.example.quizapp.GameFragments.BodyPopupImageFragment;
import com.example.quizapp.GameFragments.FourAnswersFragment;
import com.example.quizapp.GameFragments.SixAnswersFragment;
import com.example.quizapp.GameFragments.SliderFragment;
import com.example.quizapp.GameFragments.TrueFalseFragment;
import com.example.quizapp.dto.QuizDto;
import com.example.quizapp.dto.QustionType;
import com.example.quizapp.dto.ResultDto;
import com.example.quizapp.dto.TimerDto;
import com.example.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Quiz_Activity extends AppCompatActivity {

    QuizDto quizDto;
    private final Gson gson = new Gson();
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);

    Bundle bundle;

    //==============================================================================================
    TextView questionCounter;
    TextView question;
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        /*
         * Inicjowanie elementów z widoku
         */
        questionCounter = findViewById(R.id.timeClock);
        question = findViewById(R.id.question);

        game();

    }

    public void game() {
        hubConnectivity.onGame(message -> {

            bundle = new Bundle();
            quizDto = gson.fromJson(message, QuizDto.class);
            String jsonList = gson.toJson(quizDto.answers);
            String jsonDataSlider = gson.toJson(quizDto);

            switch (QustionType.parseToEnum(quizDto.getQuestionType())) {
                case SINGLE_FOUR_ANSWERS: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                    });
                    replaceFragment(new FourAnswersFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));
                    break;
                }
                case TRUE_FALSE: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                    });
                    replaceFragment(new TrueFalseFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));
                    break;
                }
                case SINGLE_SIX_ANSWERS: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                    });

                    replaceFragment(new SixAnswersFragment(), R.id.quizLargeFrameLayout, new BodyPopupImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));
                    break;
                }
                case MULTIPLE_FOUR_ANSWERS: {
                    break;
                }
                case RANGE: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                    });
                    replaceFragmentSlider(new SliderFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonDataSlider, Long.toString(quizDto.getQuestionId()));
                    break;
                }
            }
        });

        hubConnectivity.onQuestionResult(message -> {
            runOnUiThread(() -> questionCounter.setTextColor(Color.parseColor("#f7fef5")));
            ResultDto[] arrayResult = gson.fromJson(message, ResultDto[].class);
            Intent intent = new Intent(Quiz_Activity.this, ResultActivity.class);
            intent.putExtra("arrayResult", (Serializable) arrayResult);
            startActivity(intent);
        });


        hubConnectivity.onDisconnect(message -> {
            runOnUiThread(() -> Toast.makeText(Quiz_Activity.this,
                    "Rozłączono z quizem", Toast.LENGTH_LONG).show());
            finish();
        });
    }

    public void setDataQuiz() {
        question.setTextColor(Color.parseColor("#19452e"));
        question.setText(quizDto.getQuestion());

    }

    public void timerQuestion() {
        hubConnectivity.onQuestionTimer(messageTime -> {
            TimerDto timer = gson.fromJson(messageTime, TimerDto.class);
            questionCounter.setTextColor(Color.parseColor("#19452e"));
            questionCounter.setText(String.valueOf(timer.getRemaining()));
        });
    }

    //==============================================================================================

    private void replaceFragment(Fragment fragmentQuizMain, int frameLayoutQuiz, Fragment fragmentImageMain,
                                 int frameLayoutImage, String data, String id) {
        bundle.putString("data", data);
        bundle.putString("id", id);
        fragmentQuizMain.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutQuiz, fragmentQuizMain);
            fragmentTransaction.replace(frameLayoutImage, fragmentImageMain);
            runOnUiThread(() -> fragmentTransaction.commitAllowingStateLoss());
        }
    }

    private void replaceFragmentSlider(Fragment fragmentQuizMain, int frameLayoutQuiz, Fragment fragmentImageMain,
                                 int frameLayoutImage, String data, String id) {
        bundle.putString("data", data);
        bundle.putString("id", id);
        fragmentQuizMain.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutQuiz, fragmentQuizMain);
            fragmentTransaction.replace(frameLayoutImage, fragmentImageMain);
            runOnUiThread(() -> fragmentTransaction.commitAllowingStateLoss());
        }
    }

    public void imageDownload(String source) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(source)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {
            /*
             * Jeśli połączenie nie zostanie nawiązane z serwerem
             */
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace();}
            @Override
            public void onResponse(Call call, Response response) throws IOException { }
        });

    }
}