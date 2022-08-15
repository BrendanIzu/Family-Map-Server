package result;

public class LoginResult {
    String authtoken;
    String username;
    String personID;
    public boolean success;
    String message;

    public LoginResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public LoginResult(boolean failure) {
        this.message = "error: [Description of the error]";
        this.success = failure;
    }

    public String getUsername(){
        return username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
