package com.example.quizapp.LocalDataBase;

public class UserResponseBody {

    String isGood;
    String token;

    //==============================================================================================

    /*
     * Klasa odpowiedzielna za przechowywanie zwróconych danych przez serwer po udanym zalogowaniu
     */
    public UserResponseBody(String isGood, String token) {
        this.isGood = isGood;
        this.token = token;
    }

    //==============================================================================================

    public String getIsGood() {
        return isGood;
    }

    //==============================================================================================

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    //==============================================================================================

    public String getToken() {
        return token;
    }

    //==============================================================================================

    public void setToken(String token) {
        this.token = token;
    }
}