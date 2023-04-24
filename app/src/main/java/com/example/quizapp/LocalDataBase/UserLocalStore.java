package com.example.quizapp.LocalDataBase;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    //==============================================================================================

    /*
     * Klasa odpowiedzielna za przechowywanie danych oraz obsługę zalogowywania oraz wylogowywania
     * użytkownika z aplikacji
     */
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //==============================================================================================

    public void storeUserData(UserLogedData userLogedData) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", userLogedData.usernameOrEmail);
        userLocalDatabaseEditor.putString("password", userLogedData.password);
        userLocalDatabaseEditor.putString("token", userLogedData.token);
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public UserLogedData getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String login = userLocalDatabase.getString("login", "");
        String password = userLocalDatabase.getString("password", "");
        String token = userLocalDatabase.getString("token", "");

        UserLogedData userLogedData = new UserLogedData(login, password, token);
        return userLogedData;
    }
}