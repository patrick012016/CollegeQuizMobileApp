package pl.dominikpiskor.quizapp.LocalDataBase;

/**
 * The class responsible for storing the returned data by the server after a successful login
 */
public class UserResponseBody {

    /**
     * Variable declaration
     */
    String isGood;
    String token;

    public UserResponseBody(String isGood, String token) {
        this.isGood = isGood;
        this.token = token;
    }

    //==============================================================================================

    public String getIsGood() {
        return isGood;
    }

    //==============================================================================================

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    //==============================================================================================

    public String getToken() {
        return token;
    }

    //==============================================================================================

    public void setToken(String token) {
        this.token = token;
    }
}
