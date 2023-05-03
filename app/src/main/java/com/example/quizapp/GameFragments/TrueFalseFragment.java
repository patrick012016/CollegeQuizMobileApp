package com.example.quizapp.GameFragments;

import static com.example.quizapp.Utils.Constans.HUBURL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

public class TrueFalseFragment extends Fragment {
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
    CardView cardA, cardB;
    TextView answerA, answerB;
    String qeusetionId, mydataAnswers;

    public TrueFalseFragment() {
        // Required empty public constructor
    }

    public static TrueFalseFragment newInstance() {
        TrueFalseFragment fragment = new TrueFalseFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        unlockCardResult();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        cardA = getView().findViewById(R.id.cardTrue);
        cardB = getView().findViewById(R.id.cardFalse);
        answerA = getView().findViewById(R.id.answerTrue);
        answerB = getView().findViewById(R.id.answerFalse);

        mydataAnswers = bundle.getString("data");
        qeusetionId = bundle.getString("id");

        String[] array = new Gson().fromJson(mydataAnswers, String[].class);

        cardA.setOnClickListener(v -> {
            answerSend(0);
            blockCardResult(cardA, cardB);
            setScale(cardA);
        });

        cardB.setOnClickListener(v -> {
            answerSend(1);
            blockCardResult(cardA, cardB);
            setScale(cardB);
        });
    }

    public void setScale(CardView one) {
        one.setScaleX(0.85f);
        one.setScaleY(0.85f);
    }

    public void blockCardResult(CardView one, CardView two) {
        one.setFocusable(false);
        one.setClickable(false);
        two.setFocusable(false);
        two.setClickable(false);
    }

    public void unlockCardResult() {
        cardA.setFocusable(true);
        cardA.setClickable(true);
        cardB.setFocusable(true);
        cardB.setClickable(true);
        cardA.setScaleY(1);
        cardB.setScaleY(1);
        cardA.setScaleX(1);
        cardB.setScaleX(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_true_false, container, false);
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