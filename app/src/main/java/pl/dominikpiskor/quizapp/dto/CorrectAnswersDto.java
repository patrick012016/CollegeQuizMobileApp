package pl.dominikpiskor.quizapp.dto;

public class CorrectAnswersDto {
    String AnswerName;
    String key;
    String value;

    public CorrectAnswersDto(String answerName, String key, String value) {
        AnswerName = answerName;
        this.key = key;
        this.value = value;
    }

    public String getAnswerName() {
        return AnswerName;
    }

    public void setAnswerName(String answerName) {
        AnswerName = answerName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
