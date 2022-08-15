package service;

import dataAccess.DataAccessException;
import dataAccess.*;
import generator.HistoryGenerator;
import model.*;
import request.RegisterRequest;
import result.RegisterResult;

import java.io.IOException;

public class RegisterService extends Service {
    /**
     * returns a result based on the given request
     *
     * @param r request object
     * @return result object
     */
    public RegisterResult Register(RegisterRequest r) throws DataAccessException {
        User user = new User(r.username, r.password, r.email, r.firstName, r.lastName, r.gender, generateId());
        AuthToken AuthToken = new AuthToken(generateAuthToken(), user.getUser_name());
        try {
            openConnections();
            udao.insertUser(user);
            adao.insertAuthToken(AuthToken);

            Person root = new Person(user.getPerson_id(), user.getUser_name(),
                    user.getFirst_name(), user.getLast_name(), user.getGender(), generateId(), generateId(),
                    generateId());
            try {
                HistoryGenerator hg = new HistoryGenerator(4, root);
                for(Person person : hg.getPersons()) {
                    pdao.insertPerson(person);
                }
                for(Event event : hg.getEvents()) {
                    edao.insertEvent(event);
                }
            } catch(IOException ex) {
                System.out.println("Failed to add new generations");
                closeConnections(false);
                return new RegisterResult(false);
            }
            closeConnections(true);
            return new RegisterResult(AuthToken.getAuthToken(), user.getUser_name(), user.getPerson_id(), true);
        } catch(DataAccessException ex) {
            System.out.println("Failed to register new user");
            closeConnections(false);
            return new RegisterResult(false);
        }
    }
}
