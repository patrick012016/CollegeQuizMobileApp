package com.example.quizapp.dto;

import java.util.Arrays;
import java.util.Map;

public enum QustionType {
    SINGLE_FOUR_ANSWERS(1, "SINGLE_FOUR_ANSWERS"),
    TRUE_FALSE(2, "TRUE_FALSE"),
    MULTIPLE_FOUR_ANSWERS(3, "MULTIPLE_FOUR_ANSWERS"),
    SINGLE_SIX_ANSWERS(4, "SINGLE_SIX_ANSWERS"),
    RANGE(5, "RANGE"),;
    private final long id;
    private final String name;

    QustionType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean checkType(String type) {
       return this.name.equals(type);
    }

    public boolean checkType(long id) {
        return this.id == id;
    }

    public static QustionType parseToEnum (long id) {
        return Arrays.stream(values()).filter(e ->e.id == id).findFirst().orElseThrow(IllegalStateException::new);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}