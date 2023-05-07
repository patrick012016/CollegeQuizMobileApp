package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;
import static com.example.quizapp.Utils.Constans.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.GameFragments.BodyImageFragment;
import com.example.quizapp.GameFragments.BodyPopupImageFragment;
import com.example.quizapp.GameFragments.FourAnswersFragment;
import com.example.quizapp.GameFragments.FourAnswersMultiFragment;
import com.example.quizapp.GameFragments.SixAnswersFragment;
import com.example.quizapp.GameFragments.SliderCorrectFragment;
import com.example.quizapp.GameFragments.SliderFragment;
import com.example.quizapp.GameFragments.TrueFalseFragment;
import com.example.quizapp.dto.BindedAnswers;
import com.example.quizapp.dto.CorrectAnswersDto;
import com.example.quizapp.dto.QuizDto;
import com.example.quizapp.dto.QustionType;
import com.example.quizapp.dto.ResultDto;
import com.example.quizapp.dto.TimerDto;
import com.example.quizapp.hubs.HubConnectivity;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Quiz_Activity extends AppCompatActivity {

    QuizDto quizDto;
    private final Gson gson = new Gson();
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
    FragmentManager fragmentManager = getSupportFragmentManager();
    Bundle bundle, bundle1;

    //==============================================================================================
    TextView questionCounter;
    TextView question;
    ImageView imageIsMulti;
    //==============================================================================================
    @Override
    public void onBackPressed() { }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        /*
         * Inicjowanie elementów z widoku
         */
        questionCounter = findViewById(R.id.timeClock);
        question = findViewById(R.id.question);
        imageIsMulti = findViewById(R.id.imageIsMultiCorrect);
        game();
    }

    public void game() {
        hubConnectivity.onGame(message -> {

            bundle = new Bundle();
            bundle1 = new Bundle();
            quizDto = gson.fromJson(message, QuizDto.class);
            String jsonList = gson.toJson(quizDto.Answers);
            String jsonDataSlider = gson.toJson(quizDto);

            switch (QustionType.parseToEnum(quizDto.getQuestionType())) {
                case SINGLE_FOUR_ANSWERS: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                        imageIsMulti.setImageResource(R.drawable.baseline_looks_one_24);
                        imageIsMulti.setAlpha(1f);
                    });
                    replaceFragment(new FourAnswersFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()), quizDto.getImageUrl());
                    fragmentClear();
                    break;
                }
                case TRUE_FALSE: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                        imageIsMulti.setImageResource(R.drawable.baseline_looks_one_24);
                        imageIsMulti.setAlpha(1f);
                    });
                    replaceFragment(new TrueFalseFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()), quizDto.getImageUrl());
                    fragmentClear();
                    break;
                }
                case SINGLE_SIX_ANSWERS: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                        imageIsMulti.setImageResource(R.drawable.baseline_looks_one_24);
                        imageIsMulti.setAlpha(1f);
                    });
                    replaceFragment(new SixAnswersFragment(), R.id.quizLargeFrameLayout, new BodyPopupImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()), quizDto.getImageUrl());
                    fragmentClear();
                    break;
                }
                case MULTIPLE_FOUR_ANSWERS: {
                    runOnUiThread(() -> {
                        setDataQuiz();
                        timerQuestion();
                        imageIsMulti.setImageResource(R.drawable.baseline_auto_awesome_motion_24);
                        imageIsMulti.setAlpha(1f);
                    });
                    replaceFragment(new FourAnswersMultiFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonList, Long.toString(quizDto.getQuestionId()), quizDto.getImageUrl());
                    fragmentClear();
                    break;
                }
                case RANGE: {
                    runOnUiThread(() -> {
                        imageIsMulti.setAlpha(0f);
                        setDataQuiz();
                        timerQuestion();
                    });
                    replaceFragmentSlider(new SliderFragment(), R.id.quizFrameLayout, new BodyImageFragment(),
                            R.id.bodyFrameLayout, jsonDataSlider, Long.toString(quizDto.getQuestionId()), quizDto.getImageUrl());
                    fragmentClear();
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


        hubConnectivity.onCorrectAnswer(message -> {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter greyMask = new ColorMatrixColorFilter(matrix);
            Fragment fragmentCurrent = fragmentManager.findFragmentByTag("main");
            Type type = new TypeToken<List<CorrectAnswersDto>>(){}.getType();
            List<CorrectAnswersDto> correctAnswersDtoList = gson.fromJson(message, type);

            switch (QustionType.parseToEnum(quizDto.getQuestionType())) {
                case SINGLE_FOUR_ANSWERS: {
                    ConstraintLayout layoutA = fragmentCurrent.getView().findViewById(R.id.constraintLayoutA);
                    ConstraintLayout layoutB = fragmentCurrent.getView().findViewById(R.id.constraintLayoutB);
                    ConstraintLayout layoutC = fragmentCurrent.getView().findViewById(R.id.constraintLayoutC);
                    ConstraintLayout layoutD = fragmentCurrent.getView().findViewById(R.id.constraintLayoutD);
                    TextView answerA = fragmentCurrent.getView().findViewById(R.id.answerA);
                    TextView answerB = fragmentCurrent.getView().findViewById(R.id.answerB);
                    TextView answerC = fragmentCurrent.getView().findViewById(R.id.answerC);
                    TextView answerD = fragmentCurrent.getView().findViewById(R.id.answerD);

                    List<BindedAnswers> bindedAnswers = List.of(
                            new BindedAnswers(layoutA, answerA.getText().toString()),
                            new BindedAnswers(layoutB, answerB.getText().toString()),
                            new BindedAnswers(layoutC, answerC.getText().toString()),
                            new BindedAnswers(layoutD, answerD.getText().toString())
                    );

                    List<String> answersMapped = correctAnswersDtoList.stream().map(x -> x.getAnswerName()).collect(Collectors.toList());

                    bindedAnswers.stream()
                            .filter(a -> !answersMapped.contains(a.getAnswer()))
                            .forEach(a -> a.getMockedConstaintDto().getBackground().setColorFilter(greyMask));
                    break;
                }
                case TRUE_FALSE: {
                    ConstraintLayout layoutA = fragmentCurrent.getView().findViewById(R.id.constraintLayoutTrue);
                    ConstraintLayout layoutB = fragmentCurrent.getView().findViewById(R.id.constraintLayoutFalse);
                    if ((correctAnswersDtoList.get(0).getAnswerName().equals("BOOLEAN0"))) {
                        layoutB.getBackground().setColorFilter(greyMask);
                    } else if ((correctAnswersDtoList.get(0).getAnswerName().equals("BOOLEAN1"))) {
                        layoutA.getBackground().setColorFilter(greyMask);
                    }
                    break;
                }
                case SINGLE_SIX_ANSWERS: {
                    ConstraintLayout layoutLargeA = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeA);
                    ConstraintLayout layoutLargeB = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeB);
                    ConstraintLayout layoutLargeC = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeC);
                    ConstraintLayout layoutLargeD = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeD);
                    ConstraintLayout layoutLargeE = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeE);
                    ConstraintLayout layoutLargeF = fragmentCurrent.getView().findViewById(R.id.constraintLayoutLargeF);
                    TextView answerLargeA = fragmentCurrent.getView().findViewById(R.id.answerLargeA);
                    TextView answerLargeB = fragmentCurrent.getView().findViewById(R.id.answerLargeB);
                    TextView answerLargeC = fragmentCurrent.getView().findViewById(R.id.answerLargeC);
                    TextView answerLargeD = fragmentCurrent.getView().findViewById(R.id.answerLargeD);
                    TextView answerLargeE = fragmentCurrent.getView().findViewById(R.id.answerLargeE);
                    TextView answerLargeF = fragmentCurrent.getView().findViewById(R.id.answerLargeF);

                    List<BindedAnswers> bindedAnswers = List.of(
                            new BindedAnswers(layoutLargeA, answerLargeA.getText().toString()),
                            new BindedAnswers(layoutLargeB, answerLargeB.getText().toString()),
                            new BindedAnswers(layoutLargeC, answerLargeC.getText().toString()),
                            new BindedAnswers(layoutLargeD, answerLargeD.getText().toString()),
                            new BindedAnswers(layoutLargeE, answerLargeE.getText().toString()),
                            new BindedAnswers(layoutLargeF, answerLargeF.getText().toString())
                    );

                    List<String> answersMapped = correctAnswersDtoList.stream().map(x -> x.getAnswerName()).collect(Collectors.toList());

                    bindedAnswers.stream()
                            .filter(a -> !answersMapped.contains(a.getAnswer()))
                            .forEach(a -> a.getMockedConstaintDto().getBackground().setColorFilter(greyMask));
                    break;

                }
                case MULTIPLE_FOUR_ANSWERS: {
                    ConstraintLayout layoutA = fragmentCurrent.getView().findViewById(R.id.constraintLayoutMultiA);
                    ConstraintLayout layoutB = fragmentCurrent.getView().findViewById(R.id.constraintLayoutMultiB);
                    ConstraintLayout layoutC = fragmentCurrent.getView().findViewById(R.id.constraintLayoutMultiC);
                    ConstraintLayout layoutD = fragmentCurrent.getView().findViewById(R.id.constraintLayoutMultiD);
                    TextView answerA = fragmentCurrent.getView().findViewById(R.id.answerMultiA);
                    TextView answerB = fragmentCurrent.getView().findViewById(R.id.answerMultiB);
                    TextView answerC = fragmentCurrent.getView().findViewById(R.id.answerMultiC);
                    TextView answerD = fragmentCurrent.getView().findViewById(R.id.answerMultiD);

                    List<BindedAnswers> bindedAnswers = List.of(
                            new BindedAnswers(layoutA, answerA.getText().toString()),
                            new BindedAnswers(layoutB, answerB.getText().toString()),
                            new BindedAnswers(layoutC, answerC.getText().toString()),
                            new BindedAnswers(layoutD, answerD.getText().toString())
                    );

                    List<String> answersMapped = correctAnswersDtoList.stream().map(x -> x.getAnswerName()).collect(Collectors.toList());

                    bindedAnswers.stream()
                            .filter(a -> !answersMapped.contains(a.getAnswer()))
                            .forEach(a -> a.getMockedConstaintDto().getBackground().setColorFilter(greyMask));
                    break;
                }
                case RANGE: {
                    Button buttonSend = fragmentCurrent.getView().findViewById(R.id.sendSlider);
                    Button buttonLeft = fragmentCurrent.getView().findViewById(R.id.buttonRightToLeft);
                    Button buttonRight = fragmentCurrent.getView().findViewById(R.id.buttonLeftToRight);
                    RangeSlider rangeSliderMain = fragmentCurrent.getView().findViewById(R.id.rangeSlider);
                    rangeSliderMain.setEnabled(false);
                    rangeSliderMain.setClickable(false);
                    rangeSliderMain.setFocusable(false);
                    buttonSend.setVisibility(View.INVISIBLE);
                    buttonLeft.setVisibility(View.INVISIBLE);
                    buttonRight.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle();
                    Fragment fragmentNew = new SliderCorrectFragment();

                    bundle.putString("dataSlider", message);
                    fragmentNew.setArguments(bundle);
                    if (!fragmentManager.isDestroyed()) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameSliderCorrect, fragmentNew, "main");
                        runOnUiThread(() -> fragmentTransaction.commitAllowingStateLoss());
                    }
                    break;
                }
            }
        });


        hubConnectivity.onDisconnect(message -> {
            runOnUiThread(() -> Toast.makeText(Quiz_Activity.this,
                    "Rozłączono z quizem", Toast.LENGTH_SHORT).show());
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
                                 int frameLayoutImage, String data, String id, String img) {
        bundle.putString("data", data);
        bundle.putString("id", id);
        if(img != null) {
            bundle1.putString("img", img);
            fragmentImageMain.setArguments(bundle1);
        }
        fragmentQuizMain.setArguments(bundle);
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutQuiz, fragmentQuizMain, "main");
            fragmentTransaction.replace(frameLayoutImage, fragmentImageMain, "image");
            runOnUiThread(() -> fragmentTransaction.commitAllowingStateLoss());
        }
    }

    private void replaceFragmentSlider(Fragment fragmentQuizMain, int frameLayoutQuiz, Fragment fragmentImageMain,
                                 int frameLayoutImage, String data, String id, String img) {
        bundle.putString("data", data);
        bundle.putString("id", id);
        if(img != null) {
            bundle1.putString("img", img);
            fragmentImageMain.setArguments(bundle1);
        }
        fragmentQuizMain.setArguments(bundle);
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutQuiz, fragmentQuizMain, "main");
            fragmentTransaction.replace(frameLayoutImage, fragmentImageMain,"image");
            runOnUiThread(() -> fragmentTransaction.commitAllowingStateLoss());
        }
    }

    private void fragmentClear() {
        Fragment fragmentMain = getSupportFragmentManager().findFragmentByTag("main");
        Fragment fragmentImage = getSupportFragmentManager().findFragmentByTag("image");
        getSupportFragmentManager().beginTransaction().remove(fragmentMain).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().remove(fragmentImage).commitAllowingStateLoss();
    }
}