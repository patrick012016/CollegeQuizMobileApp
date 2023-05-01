package com.example.quizapp.dto;

import java.util.ArrayList;
import java.util.Map;

public class QuizDto {

    public long questionId;
    public String question;
    public long questionType;
    public ArrayList<String> answers;
    public int time_sec;

    public boolean is_range;
    public int step;
    public int min;
    public int max;
    public int min_counted;
    public int max_counted;


    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getQuestionType() {
        return questionType;
    }

    public void setQuestionType(long questionType) {
        this.questionType = questionType;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getTime_sec() {
        return time_sec;
    }

    public void setTime_sec(int time_sec) {
        this.time_sec = time_sec;
    }

    public boolean isIs_range() {
        return is_range;
    }

    public void setIs_range(boolean is_range) {
        this.is_range = is_range;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin_counted() {
        return min_counted;
    }

    public void setMin_counted(int min_counted) {
        this.min_counted = min_counted;
    }

    public int getMax_counted() {
        return max_counted;
    }

    public void setMax_counted(int max_counted) {
        this.max_counted = max_counted;
    }
}
