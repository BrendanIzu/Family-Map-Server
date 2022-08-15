package model;

/**
 *
 */
public class AuthToken {

    private String authToken;

    /**
     * username attached to the token
     */
    private String username;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * creates new AuthToken object
     *
     * @param authToken string authentication token
     * @param username username attached to authentication token
     */
    public AuthToken(String authToken, String username) {
        setAuthToken(authToken);
        setUsername(username);
    }
}
