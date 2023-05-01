package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class Quiz_Activity extends AppCompatActivity {

    ItemViewModel viewModel;
    QuizDto quizDto;
    private final Gson gson = new Gson();
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);

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


    public void game()
    {

        hubConnectivity.onGame(message -> {
            quizDto  = gson.fromJson(message, QuizDto.class);
            String jsonList = gson.toJson(quizDto.answers);





            switch (QustionType.parseToEnum(quizDto.getQuestionType()))
            {
                case SINGLE_FOUR_ANSWERS:
                {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataQuiz();
                        timerQuestion();
                    }
                });
                    replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
                    replaceFragmentWithData(new FourAnswersFragment(), R.id.quizFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()));

                    break;
                }

                case TRUE_FALSE:
                {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDataQuiz();
                            timerQuestion();
                        }
                    });
                    replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
                    replaceFragment(new TrueFalseFragment(), R.id.quizFrameLayout);


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
                    replaceFragment(new BodyPopupImageFragment(), R.id.bodyFrameLayout);
                    replaceFragment(new SixAnswersFragment(), R.id.quizLargeFrameLayout);

                    break;
                }

                case MULTIPLE_FOUR_ANSWERS:
                {
                    replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
                    replaceFragment(new FourAnswersFragment(), R.id.quizFrameLayout);

                    break;

                }
                case RANGE:
                {
                    replaceFragment(new BodyImageFragment(), R.id.bodyFrameLayout);
                    replaceFragment(new SliderFragment(), R.id.quizLargeFrameLayout);

                    break;
                }
            }
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
            questionCounter.setTextColor(Color.parseColor("#19452e"));
            questionCounter.setText(messageTime);
        });
    }

    //==============================================================================================

    private void replaceFragment(Fragment fragment, int frameLayout) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragmentWithData(Fragment fragment, int frameLayout, String data, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putString("id", id);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayout, fragment);
        fragmentTransaction.commit();
    }
}