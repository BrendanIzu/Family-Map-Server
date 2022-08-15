package service;

import dataAccess.*;
import model.AuthToken;

import java.util.Random;

public class Service {
    public Database db = new Database();
    public UserDao udao;
    public PersonDao pdao;
    public EventDao edao;
    public AuthTokenDao adao;

    public String generateId() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder id = new StringBuilder();
        Random rnd = new Random();
        while (id.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            id.append(chars.charAt(index));
        }
        return id.toString();
    }

    public String generateAuthToken() {
        return generateId();
    }

    public void openConnections() {
        try {
            db.openConnection();
            udao = new UserDao(db.getConnection());
            pdao = new PersonDao(db.getConnection());
            edao = new EventDao(db.getConnection());
            adao = new AuthTokenDao(db.getConnection());
        } catch (DataAccessException ex) {
            System.out.print("There was an error opening up the database");
        }
    }

    public void closeConnections(boolean commit) {
        try {
            db.closeConnection(commit);
        } catch (DataAccessException ex) {
            System.out.print("There was an error closing down the database");
        }
    }

    public boolean validAuth(String auth) {
        try {
            AuthToken authtoken = adao.findByAuthtoken(auth);
            udao.findByUsername(authtoken.getUsername());
            return true;
        } catch (DataAccessException ex) {
            System.out.print("There was an error validating the authtoken");
            return false;
        }
    }

    public boolean clearData() {
        try {
            udao.clearUserTable();
            pdao.clearPersonTable();
            edao.clearEventTable();
            adao.clearAuthTokenTable();
            return true;
        } catch(DataAccessException ex) {
            System.out.println("error clearing table");
            return false;
        }
    }
}
