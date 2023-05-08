package pl.dominikpiskor.quizapp.dto;

public class TimerDto {
    long Remaining;
    long Total;

    public TimerDto(long remaining, long total) {
        Remaining = remaining;
        Total = total;
    }

    public long getRemaining() {
        return Remaining;
    }

    public void setRemaining(long remaining) {
        Remaining = remaining;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }
}
