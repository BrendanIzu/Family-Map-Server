package result;

public class RegisterResult {
    public String authtoken;
    public String username;
    public String personID;
    public boolean success;
    public String message;

    public RegisterResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }
    public RegisterResult(boolean failure) {
        this.message = "error: [Description of the error]";
        this.success = failure;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
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
