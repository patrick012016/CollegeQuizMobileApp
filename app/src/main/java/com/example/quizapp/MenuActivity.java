package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.quizapp.LocalDataBase.UserLocalStore;

public class MenuActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        userLocalStore = new UserLocalStore(this);
    }

    public void test(View view) {
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
    }
}