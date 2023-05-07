package com.example.quizapp.dto;

import androidx.constraintlayout.widget.ConstraintLayout;

public class BindedAnswers {
    private ConstraintLayout constraintLayout;
    private String answer;

    public BindedAnswers(ConstraintLayout mockedConstaintDto, String answer) {
        this.constraintLayout = mockedConstaintDto;
        this.answer = answer;
    }

    public ConstraintLayout getMockedConstaintDto() {
        return constraintLayout;
    }

    public void setMockedConstaintDto(ConstraintLayout mockedConstaintDto) {
        this.constraintLayout = mockedConstaintDto;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
