package com.example.quizapp;

import static android.text.TextUtils.isEmpty;

import static com.example.quizapp.Utils.Constans.JSON;
import static com.example.quizapp.Utils.Constans.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.LocalDataBase.User;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.LocalDataBase.UserLogedData;
import com.example.quizapp.LocalDataBase.UserResponseBody;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView registrationText;
    TextView loginInput;
    TextView errorLogin;
    TextView errorPassword;
    TextView passwordInput;
    Button loginBtn;

    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Inicjowanie elementów z widoku
         */
        registrationText = findViewById(R.id.registerLink);
        loginInput = findViewById(R.id.loginInput);
        errorLogin = findViewById(R.id.spanLogin);
        passwordInput = findViewById(R.id.passwordInput);
        errorPassword = findViewById(R.id.spanPassword);
        loginBtn = findViewById(R.id.loginBtn);

        /*
         * Parametry startowe elementów wiodku
         */
        registrationText.setMovementMethod(LinkMovementMethod.getInstance());
        userLocalStore = new UserLocalStore(this);
    }

    //==============================================================================================

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //==============================================================================================

    private boolean authenticate() {
        return userLocalStore.getLoggedInUser() != null;
    }

    //==============================================================================================

    public void loginUser(View view) {
        String login;
        String password;
        login = loginInput.getText().toString();
        password = passwordInput.getText().toString();

        if (!isEmpty(login) && !isEmpty(password)) {

            /*
             * Łączenie do endpointu autoryzacji
             */
            OkHttpClient client = new OkHttpClient();
            String url = "https://dominikpiskor.pl/api/v1/dotnet/AuthAPI/LoginViaApi";
            User user = new User(login, password);
            RequestBody body = RequestBody.create(gson.toJson(user), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Connection", "close")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                /*
                 * Jeśli połączenie nie zostanie nawiązane z serwerem
                 */
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    "Nie udało nawiązać połączenia", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 401) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                errorLogin.setText(message);
                                errorPassword.setText(message);
                            }
                        });
                    }
                    if (response.code() == 200) {
                        UserResponseBody userResponseBody = gson.fromJson(response.body().string(), UserResponseBody.class);
                        UserLogedData userLogedData = new UserLogedData(login, password, userResponseBody.getToken());
                        userLocalStore.storeUserData(userLogedData);
                        userLocalStore.setUserLoggedIn(true);
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else {
            errorLogin.setText(message);
            errorPassword.setText(message);
        }
    }
}