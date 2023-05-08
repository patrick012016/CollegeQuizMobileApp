package pl.dominikpiskor.quizapp.LocalDataBase;

/**
 * The helper class responsible for storing the entered data from the user's view
 */
public class User {

    /**
     * Variable declaration
     */
    String usernameOrEmail;
    String password;

    public User(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
