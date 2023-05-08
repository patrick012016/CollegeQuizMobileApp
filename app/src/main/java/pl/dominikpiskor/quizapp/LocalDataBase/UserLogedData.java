package pl.dominikpiskor.quizapp.LocalDataBase;

/**
 * A class responsible for storing data after a successful user login
 */
public class UserLogedData {

    /**
     * Variable declaration
     */
    String usernameOrEmail;
    String password;
    String token;

    public UserLogedData(String usernameOrEmail, String password, String token) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
        this.token = token;
    }
}
