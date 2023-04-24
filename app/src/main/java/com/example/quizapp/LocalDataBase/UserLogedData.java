package com.example.quizapp.LocalDataBase;

public class UserLogedData {

    String usernameOrEmail;
    String password;
    String token;

    //==============================================================================================

    /*
     * Klasa odpowiedzielna za przechowywanie danych po poprawnym zalogowaniu użytkownika
     */
    public UserLogedData(String usernameOrEmail, String password, String token) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
        this.token = token;
    }
}