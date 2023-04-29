package com.example.quizapp;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.quizapp.Utils.Constans.messageLobbyJoin;
import static com.example.quizapp.Utils.Constans.messageLobbyWait;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapp.R;

import java.util.Timer;

public class LobbyActivity extends AppCompatActivity {

    TextView lobbyWait, lobbyCounter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        lobbyWait = findViewById(R.id.textLobbyWait);
        lobbyCounter = findViewById(R.id.textCounterLobby);
        progressBar = findViewById(R.id.progressBarLobby);
        lobbyWait.setText(Html.fromHtml(messageLobbyWait
                +  "<strong> \"Testowy quiz\" </strong> przez hosta...", FROM_HTML_MODE_COMPACT));

       // testowaSee();
    }


    public void testowaSee ()
    {
        progressBar.setVisibility(View.INVISIBLE);
        lobbyWait.setText(Html.fromHtml(messageLobbyJoin
                +  "<strong> \"Testowy quiz\" </strong> ururchamia się za:", FROM_HTML_MODE_COMPACT));
        new CountDownTimer(5000, 1000) {

            public void onTick(long milisec) {
                lobbyCounter.setText(String.valueOf(milisec / 1000));
            }

            public void onFinish() {
            }

        }.start();

    }
}