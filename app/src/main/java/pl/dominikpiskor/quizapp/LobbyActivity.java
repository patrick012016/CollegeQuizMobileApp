package pl.dominikpiskor.quizapp;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.dominikpiskor.quizapp.LocalDataBase.UserLocalStore;

import pl.dominikpiskor.quizapp.dto.LobbyDto;
import pl.dominikpiskor.quizapp.hubs.HubConnectivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.dominikpiskor.quizapp.Utils.Constans;

public class LobbyActivity extends AppCompatActivity {
    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(Constans.HUBURL);
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
                        "Opuszczono quiz", Toast.LENGTH_SHORT).show());
                finish();
            }
        });
    }


    public void startLobby (String nameQuiz) {
        progressBar.setVisibility(View.INVISIBLE);
        lobbyCounter.setTextColor(Color.parseColor("#19452e"));
        lobbyWait.setText(Html.fromHtml(Constans.messageLobbyJoin
                +  "<strong> \"" + nameQuiz + "\" </strong> uruchamia się za:", FROM_HTML_MODE_COMPACT));
    }

    public void connectLobby(String item) {
        hubConnectivity.connect();
        hubConnectivity.start();
        /*
         * Łączenie do lobby
         */
        OkHttpClient client = new OkHttpClient();
        String url = "https://dominikpiskor.pl/api/v1/dotnet/QuizSessionAPI/JoinRoomJwt/" + hubConnectivity.getIpConenction() + "/"+ item;
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
                        Constans.CONNECTION_ERROR, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 401) {
                    runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                            Constans.CONNECTION_ERROR, Toast.LENGTH_SHORT).show());
                }
                if (response.code() == 200) {
                    lobbyDto = gson.fromJson(response.body().string(), LobbyDto.class);
                    if(!lobbyDto.isGood()) {
                        finish();
                        runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                                Constans.CODE_ERROR, Toast.LENGTH_SHORT).show());
                    }
                    else {
                        runOnUiThread(() -> lobbyWait.setText(Html.fromHtml(Constans.messageLobbyWait
                                + "<strong> \"" + lobbyDto.getQuizName() + "\" </strong> przez hosta...", FROM_HTML_MODE_COMPACT)));
                    }
                }
            }
        });
        final Consumer<String> onDisconnectExpression = message ->
        {
            runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                    "Rozłączono z quizem", Toast.LENGTH_SHORT).show());
            finish();
        };
        hubConnectivity.onDisconnect(onDisconnectExpression);

        hubConnectivity.onCounting(message -> {
//            MediaPlayer music5 = MediaPlayer.create(LobbyActivity.this, R.raw.five);
//            MediaPlayer music4 = MediaPlayer.create(LobbyActivity.this, R.raw.four);
//            MediaPlayer music3 = MediaPlayer.create(LobbyActivity.this, R.raw.three);
//            MediaPlayer music2 = MediaPlayer.create(LobbyActivity.this, R.raw.two);
//            MediaPlayer music1 = MediaPlayer.create(LobbyActivity.this, R.raw.one);
//
//            if(Objects.equals(message, "5")) {
//                music5.start();
//            }
//           else if(Objects.equals(message, "4")) {
//                music4.start();
//            }
//            else if(Objects.equals(message, "3")) {
//                music3.start();
//            }
//            else if(Objects.equals(message, "2")) {
//                music2.start();
//            }
//            else if(Objects.equals(message, "1")) {
//                music1.start();
//            }
//            music1.setOnCompletionListener(MediaPlayer::release);
//            music2.setOnCompletionListener(MediaPlayer::release);
//            music3.setOnCompletionListener(MediaPlayer::release);
//            music4.setOnCompletionListener(MediaPlayer::release);
//            music5.setOnCompletionListener(MediaPlayer::release);
            startLobby(lobbyDto.getQuizName());
            lobbyCounter.setText(message);
            if(Objects.equals(message, "0")) {
                Intent intent = new Intent(LobbyActivity.this, Quiz_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}