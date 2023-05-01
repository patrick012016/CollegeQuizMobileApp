package com.example.quizapp.GameFragments;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.quizapp.Utils.Constans.CODE_ERROR;
import static com.example.quizapp.Utils.Constans.CONNECTION_ERROR;
import static com.example.quizapp.Utils.Constans.HUBURL;
import static com.example.quizapp.Utils.Constans.messageLobbyWait;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Fragments.ItemViewModel;
import com.example.quizapp.LobbyActivity;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.R;
import com.example.quizapp.dto.LobbyDto;
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
    public FourAnswersFragment() {
        // Required empty public constructor
    }

    public static FourAnswersFragment newInstance() {
        FourAnswersFragment fragment = new FourAnswersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answerSend(0);
                cardA.setScaleX(0.85f);
                cardA.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSend(2);
                cardB.setScaleX(0.85f);
                cardB.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSend(1);
                cardC.setScaleX(0.85f);
                cardC.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);
            }
        });
        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSend(3);
                cardD.setScaleX(0.85f);
                cardD.setScaleY(0.85f);
                blockCardResult(cardA, cardB, cardC, cardD);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four_answers, container, false);
    }

    public void blockCardResult (CardView one, CardView two, CardView three, CardView four)
    {
        one.setFocusable(false);
        one.setClickable(false);
        two.setFocusable(false);
        two.setClickable(false);
        three.setFocusable(false);
        three.setClickable(false);
        four.setFocusable(false);
        four.setClickable(false);
    }

    public void answerSend(int idAnswer)
    {
        String ipConnection = hubConnectivity.getIpConenction();
        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:5157/api/v1/dotnet/QuizSessionAPI/SendAnswerJwt/"+ ipConnection +"/"+ qeusetionId +"/" + idAnswer + "/false";
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
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 401) {

                }
                else {

                }
            }
        });
    }
}



