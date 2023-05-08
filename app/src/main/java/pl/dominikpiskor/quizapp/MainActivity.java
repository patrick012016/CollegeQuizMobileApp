package pl.dominikpiskor.quizapp;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.dominikpiskor.quizapp.LocalDataBase.User;
import pl.dominikpiskor.quizapp.LocalDataBase.UserLocalStore;
import pl.dominikpiskor.quizapp.LocalDataBase.UserLogedData;
import pl.dominikpiskor.quizapp.LocalDataBase.UserResponseBody;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.dominikpiskor.quizapp.Utils.Constans;

public class MainActivity extends AppCompatActivity {

    TextView registrationText;
    TextView loginInput;
    TextView errorLogin;
    TextView errorPassword;
    TextView passwordInput;
    Button loginBtn;
    MotionLayout motionLayout;
    ProgressBar progressBar;

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
        motionLayout = findViewById(R.id.motionLayout);
        progressBar = findViewById(R.id.progressBarAuth);

        /*
         * Parametry startowe elementów wiodku
         */
        registrationText.setMovementMethod(LinkMovementMethod.getInstance());
        userLocalStore = UserLocalStore.getInstance(this);
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
            RequestBody body = RequestBody.create(gson.toJson(user), Constans.JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Connection", "close")
                    .build();

            motionLayout.setAlpha(0.4f);
            progressBar.setVisibility(View.VISIBLE);

            client.newCall(request).enqueue(new Callback() {

                /*
                 * Jeśli połączenie nie zostanie nawiązane z serwerem
                 */
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        motionLayout.setAlpha(1);
                        Toast.makeText(MainActivity.this,
                                "Nie udało nawiązać połączenia", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        motionLayout.setAlpha(1);
                    });
                    if (response.code() == 401) {
                        runOnUiThread(() -> {
                            errorLogin.setText(Constans.message);
                            errorPassword.setText(Constans.message);
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
            errorLogin.setText(Constans.message);
            errorPassword.setText(Constans.message);
        }
    }
}