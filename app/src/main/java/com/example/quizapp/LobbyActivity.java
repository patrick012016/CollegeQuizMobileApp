package com.example.quizapp;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.quizapp.Utils.Constans.CODE_ERROR;
import static com.example.quizapp.Utils.Constans.CONNECTION_ERROR;
import static com.example.quizapp.Utils.Constans.HUBURL;
import static com.example.quizapp.Utils.Constans.JSON;
import static com.example.quizapp.Utils.Constans.message;
import static com.example.quizapp.Utils.Constans.messageLobbyJoin;
import static com.example.quizapp.Utils.Constans.messageLobbyWait;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Fragments.ItemViewModel;
import com.example.quizapp.LocalDataBase.User;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.LocalDataBase.UserLogedData;
import com.example.quizapp.LocalDataBase.UserResponseBody;
import com.example.quizapp.R;
import com.example.quizapp.dto.LobbyDto;
import com.example.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LobbyActivity extends AppCompatActivity {
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);
    TextView lobbyWait, lobbyCounter;
    ProgressBar progressBar;
    LobbyDto lobbyDto;
    Button buttonLeave;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        lobbyWait = findViewById(R.id.textLobbyWait);
        lobbyCounter = findViewById(R.id.textCounterLobby);
        progressBar = findViewById(R.id.progressBarLobby);
        buttonLeave = findViewById(R.id.buttonLeaveLobby);
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        connectLobby(token);
        buttonLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hubConnectivity.dispose();
                runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                        "Opuszczono quiz", Toast.LENGTH_LONG).show());
                finish();
            }
        });
    }


    public void startLobby (String nameQuiz) {
        progressBar.setVisibility(View.INVISIBLE);
        lobbyCounter.setTextColor(Color.parseColor("#19452e"));
        lobbyWait.setText(Html.fromHtml(messageLobbyJoin
                +  "<strong> \"" + nameQuiz + "\" </strong> uruchamia się za:", FROM_HTML_MODE_COMPACT));
    }

    public void connectLobby(String item) {
        hubConnectivity.connect();
        hubConnectivity.start();
        /*
         * Łączenie do lobby
         */
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:5157/api/v1/dotnet/QuizSessionAPI/JoinRoomJwt/" + hubConnectivity.getIpConenction() + "/"+ item;
        Request request = new Request.Builder()
                .url(url)
                .method("post", null)
                .header("Authorization", "Bearer " + UserLocalStore.getInstance(this).getUserLocalDatabase())
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {

            /*
             * Jeśli połączenie nie zostanie nawiązane z serwerem
             */
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                        CONNECTION_ERROR, Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 401) {
                    runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                            CONNECTION_ERROR, Toast.LENGTH_LONG).show());
                }
                if (response.code() == 200) {
                    lobbyDto = gson.fromJson(response.body().string(), LobbyDto.class);
                    if(!lobbyDto.isGood()) {
                        finish();
                        runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                                CODE_ERROR, Toast.LENGTH_LONG).show());
                    }
                    else {
                        runOnUiThread(() -> lobbyWait.setText(Html.fromHtml(messageLobbyWait
                                + "<strong> \"" + lobbyDto.getQuizName() + "\" </strong> przez hosta...", FROM_HTML_MODE_COMPACT)));
                    }
                }
            }
        });
        final Consumer<String> onDisconnectExpression = message ->
        {
            runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                    "Rozłączono z quizem", Toast.LENGTH_LONG).show());
            finish();
        };
        hubConnectivity.onDisconnect(onDisconnectExpression);

        hubConnectivity.onCounting(message -> {
            startLobby(lobbyDto.getQuizName());
            lobbyCounter.setText(message);
            if(Objects.equals(message, "0")) {
                Intent intent = new Intent(LobbyActivity.this, Quiz_Activity.class);
                startActivity(intent);
            }
        });
    }
}