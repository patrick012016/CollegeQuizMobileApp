package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Fragments.ItemViewModel;
import com.example.quizapp.GameFragments.BodyImageFragment;
import com.example.quizapp.GameFragments.BodyPopupImageFragment;
import com.example.quizapp.GameFragments.FourAnswersFragment;
import com.example.quizapp.GameFragments.SixAnswersFragment;
import com.example.quizapp.GameFragments.SliderFragment;
import com.example.quizapp.GameFragments.TrueFalseFragment;
import com.example.quizapp.dto.LobbyDto;
import com.example.quizapp.dto.QuizDto;
import com.example.quizapp.dto.QustionType;
import com.example.quizapp.dto.ResultDto;
import com.example.quizapp.dto.TimerDto;
import com.example.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.io.Serializable;
import java.sql.Array;
import java.util.List;

public class Quiz_Activity extends AppCompatActivity {

    ItemViewModel viewModel;
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

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.executePendingTransactions();
//    }

//    @Override
//    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//    }

    public void game()
    {

        hubConnectivity.onGame(message -> {
            bundle = new Bundle();
            quizDto  = gson.fromJson(message, QuizDto.class);
            String jsonList = gson.toJson(quizDto.answers);


            switch (QustionType.parseToEnum(quizDto.getQuestionType()))
            {
                case SINGLE_FOUR_ANSWERS:
                {
                    System.out.println("test2");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataQuiz();
                        timerQuestion();
                    }
                });
                    System.out.println("420");
                    replaceFragment(new FourAnswersFragment(), R.id.quizFrameLayout, new BodyImageFragment(), R.id.bodyFrameLayout,
                            jsonList, Long.toString(quizDto.getQuestionId()));
                    System.out.println("421");
                  //  replaceFragmentWithData(new FourAnswersFragment(), R.id.quizFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));
                    System.out.println("test3333333333333333333333");
                    break;
                }

                case TRUE_FALSE:
                {
                    System.out.println("test1");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDataQuiz();
                            timerQuestion();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(new TrueFalseFragment(), R.id.quizFrameLayout, new BodyImageFragment(), R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));

                        }
                    });



                    break;
                }

                case SINGLE_SIX_ANSWERS:
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDataQuiz();
                            timerQuestion();
                        }
                    });
                 //   replaceFragment(new BodyPopupImageFragment(), R.id.bodyFrameLayout);
                 //   replaceFragment(new SixAnswersFragment(), R.id.quizLargeFrameLayout);

                    break;
                }

                case MULTIPLE_FOUR_ANSWERS:
                {
                 //   replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
                //    replaceFragment(new FourAnswersFragment(), R.id.quizFrameLayout);

                    break;

                }
                case RANGE:
                {
              //      replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
              //      replaceFragment(new SliderFragment(), R.id.quizLargeFrameLayout);

                    break;
                }
            }
        });

        hubConnectivity.onQuestionResult(message -> {
            ResultDto[] arrayResult  = gson.fromJson(message, ResultDto[].class);


            Intent intent = new Intent(Quiz_Activity.this, ResultActivity.class);
            intent.putExtra("arrayResult", (Serializable) arrayResult);
            startActivity(intent);
           // finish();

        });





        hubConnectivity.onDisconnect(message -> {
            runOnUiThread(() -> Toast.makeText(Quiz_Activity.this,
                    "Rozłączono z quizem", Toast.LENGTH_LONG).show());
            finish();
        });
    }

    public void setDataQuiz()
    {
        question.setTextColor(Color.parseColor("#19452e"));
        question.setText(quizDto.getQuestion());

    }

    public void timerQuestion()
    {
        hubConnectivity.onQuestionTimer(messageTime -> {
            TimerDto timer = gson.fromJson(messageTime, TimerDto.class);
            questionCounter.setTextColor(Color.parseColor("#19452e"));
            questionCounter.setText(String.valueOf(timer.getElapsed()));
        });
    }

    //==============================================================================================

    private void replaceFragment(Fragment fragment, int frameLayout, Fragment fragment1, int frameLayout1, String data, String id) {
        bundle.putString("data", data);
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        System.out.println("punkreplace0");
        FragmentManager fragmentManager = getSupportFragmentManager();
        System.out.println("punkreplace1");
        if(!fragmentManager.isDestroyed()){
            System.out.println("punkreplace2");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            System.out.println("punkreplace3");
            fragmentTransaction.replace(frameLayout, fragment);
            System.out.println("punkreplace4");
            fragmentTransaction.replace(frameLayout1, fragment1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentTransaction.commitAllowingStateLoss();
                }
            });

            System.out.println("punkreplace5");
        }

    }

//    private void replaceFragmentWithData(Fragment fragment, int frameLayout, String data, String id) {
//        bundle.putString("data", data);
//        bundle.putString("id", id);
//        fragment.setArguments(bundle);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        if(!fragmentManager.isDestroyed()) {
//
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(frameLayout, fragment);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    fragmentTransaction.commitAllowingStateLoss();
//                    fragmentTransaction.commit();
//                }
//            });
//
//        }
//    }

//    private void testFragmenr(Fragment fragment)
//    {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.commit();
//        fragmentTransaction.remove(fragment).commit();
//    }
}