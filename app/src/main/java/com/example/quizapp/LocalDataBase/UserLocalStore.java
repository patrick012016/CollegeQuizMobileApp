package com.example.quizapp.LocalDataBase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quizapp.hubs.HubConnectivity;

public class UserLocalStore {

    private static UserLocalStore instance;
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    //==============================================================================================

    public static UserLocalStore getInstance(Context context)
    {
        if (instance == null)
            instance = new UserLocalStore(context);
        return instance;
    }

    //==============================================================================================

    /*
     * Klasa odpowiedzielna za przechowywanie danych oraz obsługę zalogowywania oraz wylogowywania
     * użytkownika z aplikacji
     */
    private UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //==============================================================================================

    public String getUserLocalDatabase() {
        return userLocalDatabase.getString("token", "");
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