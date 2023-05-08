package pl.dominikpiskor.quizapp.dto;

/**
 * The class responsible for mapping correct data for slider question type
 */
public class CorrectSliderDto {
    String AnswerName;
    int AnswerCorrect;
    int AnswerMin;
    int AnswerMax;
    int AnswerMinCounted;
    int AnswerMaxCounted;
    int AnswerStep;

    public CorrectSliderDto(String answerName, int answerCorrect, int answerMin, int answerMax,
                            int answerMinCounted, int answerMaxCounted, int answerStep) {
        AnswerName = answerName;
        AnswerCorrect = answerCorrect;
        AnswerMin = answerMin;
        AnswerMax = answerMax;
        AnswerMinCounted = answerMinCounted;
        AnswerMaxCounted = answerMaxCounted;
        AnswerStep = answerStep;
    }

    public String getAnswerName() {
        return AnswerName;
    }

    public void setAnswerName(String answerName) {
        AnswerName = answerName;
    }

    public int getAnswerCorrect() {
        return AnswerCorrect;
    }

    public void setAnswerCorrect(int answerCorrect) {
        AnswerCorrect = answerCorrect;
    }

    public int getAnswerMin() {
        return AnswerMin;
    }

    public void setAnswerMin(int answerMin) {
        AnswerMin = answerMin;
    }

    public int getAnswerMax() {
        return AnswerMax;
    }

    public void setAnswerMax(int answerMax) {
        AnswerMax = answerMax;
    }

    public int getAnswerMinCounted() {
        return AnswerMinCounted;
    }

    public void setAnswerMinCounted(int answerMinCounted) {
        AnswerMinCounted = answerMinCounted;
    }

    public int getAnswerMaxCounted() {
        return AnswerMaxCounted;
    }

    public void setAnswerMaxCounted(int answerMaxCounted) {
        AnswerMaxCounted = answerMaxCounted;
    }

    public int getAnswerStep() {
        return AnswerStep;
    }

    public void setAnswerStep(int answerStep) {
        AnswerStep = answerStep;
    }
}
