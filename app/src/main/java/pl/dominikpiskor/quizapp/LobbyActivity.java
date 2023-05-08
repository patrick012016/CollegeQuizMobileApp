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

/**
 * The class responsible for rendering the waiting room view and handling the waiting room logic
 */
public class LobbyActivity extends AppCompatActivity {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(Constans.HUBURL);

    /**
     * Initializing items from the view
     */
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
        buttonLeave.setOnClickListener(v -> {
            hubConnectivity.dispose();
            runOnUiThread(() -> Toast.makeText(LobbyActivity.this,
                    "Opuszczono quiz", Toast.LENGTH_SHORT).show());
            finish();
        });
    }

    //==============================================================================================

    /**
     * The method responsible for timer counting
     * @param nameQuiz
     */
    public void startLobby (String nameQuiz) {
        progressBar.setVisibility(View.INVISIBLE);
        lobbyCounter.setTextColor(Color.parseColor("#19452e"));
        lobbyWait.setText(Html.fromHtml(Constans.messageLobbyJoin
                +  "<strong> \"" + nameQuiz + "\" </strong> uruchamia się za:", FROM_HTML_MODE_COMPACT));
    }

    //==============================================================================================

    /**
     * The method responsible for joining the lobby
     * @param item lobby code
     */
    public void connectLobby(String item) {
        hubConnectivity.connect();
        hubConnectivity.start();

        OkHttpClient client = new OkHttpClient();
        String url = "https://dominikpiskor.pl/api/v1/dotnet/QuizSessionAPI/JoinRoomJwt/" + hubConnectivity.getIpConenction() + "/"+ item;
        Request request = new Request.Builder()
                .url(url)
                .method("post", null)
                .header("Authorization", "Bearer " + UserLocalStore.getInstance(this).getUserLocalDatabase())
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {

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
