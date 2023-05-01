package com.example.quizapp.dto;

public class LobbyDto {

    boolean isGood;
    String screenType;
    String quizName;

    public LobbyDto(boolean isGood, String screenType, String quizName) {
        this.isGood = isGood;
        this.screenType = screenType;
        this.quizName = quizName;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
}
