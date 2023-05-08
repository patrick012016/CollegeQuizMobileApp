package pl.dominikpiskor.quizapp.dto;

import java.io.Serializable;

public class ResultDto implements Serializable {

    String Username;
    long Score;
    boolean isLast;
    long newPoints;
    int CurrentStreak;

    public ResultDto(String username, long score, boolean isLast, long newPoints, int currentStreak) {
        this.Username = username;
        this.Score = score;
        this.isLast = isLast;
        this.newPoints = newPoints;
        this.CurrentStreak = currentStreak;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        this.Score = score;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public long getNewPoints() {
        return newPoints;
    }

    public void setNewPoints(long newPoints) {
        this.newPoints = newPoints;
    }

    public int getCurrentStreak() {
        return CurrentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.CurrentStreak = currentStreak;
    }
}
