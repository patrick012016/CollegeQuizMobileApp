package pl.dominikpiskor.quizapp.LocalDataBase;

public class User {

    String usernameOrEmail;
    String password;

    //==============================================================================================

    /*
     * Klasa odpowiedzielna za przechowywanie wprowadzonych danych z widoku użytkownika
     */
    public User(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}