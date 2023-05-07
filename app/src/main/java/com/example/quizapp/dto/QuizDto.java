package com.example.quizapp.dto;

import java.util.ArrayList;

public class QuizDto {

    public long QuestionId;
    public String Question;
    public long QuestionType;
    public ArrayList<String> Answers;
    public int TimeSec;
    public boolean IsRange;
    public int Step;
    public int Min;
    public int Max;
    public int MinCounted;
    public int MaxCounted;
    public String ImageUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public long getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(long questionId) {
        this.QuestionId = questionId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public long getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(long questionType) {
        this.QuestionType = questionType;
    }

    public ArrayList<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.Answers = answers;
    }

    public int getTimeSec() {
        return TimeSec;
    }

    public void setTimeSec(int timeSec) {
        this.TimeSec = timeSec;
    }

    public boolean isRange() {
        return IsRange;
    }

    public void setRange(boolean range) {
        this.IsRange = range;
    }

    public int getStep() {
        return Step;
    }

    public void setStep(int step) {
        this.Step = step;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        this.Min = min;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        this.Max = max;
    }

    public int getMinCounted() {
        return MinCounted;
    }

    public void setMinCounted(int minCounted) {
        this.MinCounted = minCounted;
    }

    public int getMaxCounted() {
        return MaxCounted;
    }

    public void setMaxCounted(int maxCounted) {
        this.MaxCounted = maxCounted;
    }
}
