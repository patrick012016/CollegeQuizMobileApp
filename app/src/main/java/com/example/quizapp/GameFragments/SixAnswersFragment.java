package com.example.quizapp.GameFragments;

import static com.example.quizapp.Utils.Constans.HUBURL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.R;
import com.example.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SixAnswersFragment extends Fragment {
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
    CardView cardA, cardB, cardC, cardD, cardE, cardF;
    TextView answerA, answerB, answerC, answerD, answerE, answerF;
    String qeusetionId, mydataAnswers;

    public SixAnswersFragment() { }

    public static SixAnswersFragment newInstance() {
        SixAnswersFragment fragment = new SixAnswersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        unlockCardResult();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        cardA = getView().findViewById(R.id.cardLargeA);
        cardB = getView().findViewById(R.id.cardLargeB);
        cardC = getView().findViewById(R.id.cardLargeC);
        cardD = getView().findViewById(R.id.cardLargeD);
        cardE = getView().findViewById(R.id.cardLargeE);
        cardF = getView().findViewById(R.id.cardLargeF);
        answerA = getView().findViewById(R.id.answerLargeA);
        answerB = getView().findViewById(R.id.answerLargeB);
        answerC = getView().findViewById(R.id.answerLargeC);
        answerD = getView().findViewById(R.id.answerLargeD);
        answerE = getView().findViewById(R.id.answerLargeE);
        answerF = getView().findViewById(R.id.answerLargeF);

        mydataAnswers = bundle.getString("data");
        qeusetionId = bundle.getString("id");

        String[] array = new Gson().fromJson(mydataAnswers, String[].class);
        answerA.setText(array[0]);
        answerB.setText(array[1]);
        answerC.setText(array[2]);
        answerD.setText(array[3]);
        answerE.setText(array[4]);
        answerF.setText(array[5]);

        cardA.setOnClickListener(v -> {
            answerSend(0);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardA);
        });
        cardB.setOnClickListener(v -> {
            answerSend(1);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardB);
        });
        cardC.setOnClickListener(v -> {
            answerSend(2);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardC);
        });
        cardD.setOnClickListener(v -> {
            answerSend(3);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardD);
        });
        cardE.setOnClickListener(v -> {
            answerSend(4);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardE);
        });
        cardF.setOnClickListener(v -> {
            answerSend(5);
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
            blockCardResult(cardA, cardB, cardC, cardD, cardE, cardF);
            setScale(cardF);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_six_answers, container, false);
    }
    public void setScale(CardView one) {
        one.setScaleX(0.85f);
        one.setScaleY(0.85f);
    }

    public void blockCardResult(CardView one, CardView two, CardView three, CardView four, CardView five, CardView six) {
        one.setFocusable(false);
        one.setClickable(false);
        two.setFocusable(false);
        two.setClickable(false);
        three.setFocusable(false);
        three.setClickable(false);
        four.setFocusable(false);
        four.setClickable(false);
        five.setFocusable(false);
        five.setClickable(false);
        six.setFocusable(false);
        six.setClickable(false);
    }

    public void unlockCardResult() {
        cardA.setFocusable(true);
        cardA.setClickable(true);
        cardB.setFocusable(true);
        cardB.setClickable(true);
        cardC.setFocusable(true);
        cardC.setClickable(true);
        cardD.setFocusable(true);
        cardD.setClickable(true);
        cardE.setFocusable(true);
        cardE.setClickable(true);
        cardF.setFocusable(true);
        cardF.setClickable(true);
        cardA.setScaleY(1);
        cardB.setScaleY(1);
        cardC.setScaleY(1);
        cardD.setScaleY(1);
        cardE.setScaleY(1);
        cardF.setScaleY(1);
        cardA.setScaleX(1);
        cardB.setScaleX(1);
        cardC.setScaleX(1);
        cardD.setScaleX(1);
        cardE.setScaleX(1);
        cardF.setScaleX(1);
    }

    public void answerSend(int idAnswer) {
        String ipConnection = hubConnectivity.getIpConenction();
        OkHttpClient client = new OkHttpClient();

        String url = "https://dominikpiskor.pl/api/v1/dotnet/QuizSessionAPI/SendAnswerJwt/" + ipConnection + "/" + qeusetionId + "/" + idAnswer + "/false";
        Request request = new Request.Builder()
                .url(url)
                .method("post", null)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + UserLocalStore.getInstance(getContext()).getUserLocalDatabase())
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