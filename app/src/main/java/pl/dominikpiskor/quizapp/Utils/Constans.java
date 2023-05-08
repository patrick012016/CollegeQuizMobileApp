package pl.dominikpiskor.quizapp.Utils;

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

    public static final String HUBURL = "https://dominikpiskor.pl/quizUserSessionHub";
    public static final String CODE_REGEX = "[A-Z]{5}";
    public static final String CONNECTION_ERROR = "Nie udało się dołączyć";
    public static final String CODE_ERROR = "Podany kod nie istnieje lub uległ przedawnieniu";

    public static final String[] arrayResultColors = {"#1fd2e6", "#29e4c5", "#55e5a0", "#bbf5cd", "#bbf5cd"};


    //==============================================================================================

    private Constans() {
    }
}