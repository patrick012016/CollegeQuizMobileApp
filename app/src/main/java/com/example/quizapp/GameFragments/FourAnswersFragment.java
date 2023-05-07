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

public class FourAnswersFragment extends Fragment {
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
    CardView cardA, cardB, cardC, cardD;
    TextView answerA, answerB, answerC, answerD;
    String qeusetionId, mydataAnswers;

    public FourAnswersFragment() { }

    public static FourAnswersFragment newInstance() {
        FourAnswersFragment fragment = new FourAnswersFragment();
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
        cardA = getView().findViewById(R.id.cardA);
        cardB = getView().findViewById(R.id.cardB);
        cardC = getView().findViewById(R.id.cardC);
        cardD = getView().findViewById(R.id.cardD);
        answerA = getView().findViewById(R.id.answerA);
        answerB = getView().findViewById(R.id.answerB);
        answerC = getView().findViewById(R.id.answerC);
        answerD = getView().findViewById(R.id.answerD);

        mydataAnswers = bundle.getString("data");
        qeusetionId = bundle.getString("id");

        String[] array = new Gson().fromJson(mydataAnswers, String[].class);
        answerA.setText(array[0]);
        answerB.setText(array[1]);
        answerC.setText(array[2]);
        answerD.setText(array[3]);


        cardA.setOnClickListener(v -> {
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            answerSend(0);
            blockCardResult(cardA, cardB, cardC, cardD);
            setScale(cardA);
        });
        cardB.setOnClickListener(v -> {
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            answerSend(1);
            blockCardResult(cardA, cardB, cardC, cardD);
            setScale(cardB);
        });
        cardC.setOnClickListener(v -> {
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            answerSend(2);
            blockCardResult(cardA, cardB, cardC, cardD);
            setScale(cardC);
        });
        cardD.setOnClickListener(v -> {
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            answerSend(3);
            blockCardResult(cardA, cardB, cardC, cardD);
            setScale(cardD);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four_answers, container, false);
    }

    public void setScale(CardView one) {
        one.setScaleX(0.85f);
        one.setScaleY(0.85f);
    }

    public void blockCardResult(CardView one, CardView two, CardView three, CardView four) {
        one.setFocusable(false);
        one.setClickable(false);
        two.setFocusable(false);
        two.setClickable(false);
        three.setFocusable(false);
        three.setClickable(false);
        four.setFocusable(false);
        four.setClickable(false);
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
        cardA.setScaleY(1);
        cardB.setScaleY(1);
        cardC.setScaleY(1);
        cardD.setScaleY(1);
        cardA.setScaleX(1);
        cardB.setScaleX(1);
        cardC.setScaleX(1);
        cardD.setScaleX(1);
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
