package com.example.quizapp;

import static android.text.TextUtils.isEmpty;

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

public class MainActivity extends AppCompatActivity {
    TextView registrationText;
    TextView loginInput;
    TextView errorLogin;
    TextView errorPassword;
    TextView passwordInput;
    Button loginBtn;
    String message = "Podaj poprawne dane!";
    UserLocalStore userLocalStore;

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

        @Override
        protected void onStart() {
            super.onStart();
            if (authenticate() == true) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }

        private boolean authenticate() {
            return userLocalStore.getLoggedInUser() != null;
        }

        public void loginUser (View view){
        String login;
        String password;
        login = loginInput.getText().toString();
        password = passwordInput.getText().toString();

        if (!isEmpty(login) && !isEmpty(password)) {
            //zaciaganie danych z API

            if(login.equals("admin") && password.equals("admin")) {

                User user = new User(login, password);

                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);

                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();

            }
            else {
                errorLogin.setText(message);
                errorPassword.setText(message);
            }
        }
        else {
            errorLogin.setText(message);
            errorPassword.setText(message);
        }
    }
}
