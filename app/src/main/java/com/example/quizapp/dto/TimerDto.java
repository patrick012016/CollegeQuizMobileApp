package com.example.quizapp.dto;

public class TimerDto {
    long Elapsed;
    long Total;

    public TimerDto(long elapsed, long total) {
        Elapsed = elapsed;
        Total = total;
    }

    public long getElapsed() {
        return Elapsed;
    }

    public void setElapsed(long elapsed) {
        Elapsed = elapsed;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }
}
