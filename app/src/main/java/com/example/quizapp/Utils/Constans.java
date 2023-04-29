package com.example.quizapp.Utils;

import okhttp3.MediaType;

public class Constans {

    /*
     * Zmienne stałe globalne
     */
    public static final String message = "Podaj poprawne dane!";
    public static final String messageLobbyWait = "Oczekiwanie na uruchomienie " +
            "quizu";
    public static final String messageLobbyJoin = "Przygotuj się! Quiz ";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //==============================================================================================

    private Constans() {
    }
}