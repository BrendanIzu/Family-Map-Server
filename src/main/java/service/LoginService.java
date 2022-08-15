package service;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;
import result.Result;

public class LoginService extends Service{
    /**
     * returns a result based on the given request
     *
     * @param r request object
     * @return result object
     */
    public LoginResult Login(LoginRequest r) throws DataAccessException {
        try {
            openConnections();
            User user = udao.findByUsername(r.username);
            // If the password the user entered matched the password in the database
            if(user.getPassword().equals(r.password)) {
                AuthToken authToken = new AuthToken(generateAuthToken(), user.getUser_name());
                adao.insertAuthToken(authToken);
                closeConnections(true);
                return new LoginResult(authToken.getAuthToken(), user.getUser_name(), user.getPerson_id(), true);
            }
            else {
                System.out.println("Passwords do not match");
                closeConnections(false);
                return new LoginResult(false);
            }
        } catch(DataAccessException ex) {
            closeConnections(false);
            return new LoginResult(false);
        }
    }
}
