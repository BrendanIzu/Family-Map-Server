package service;

import dataAccess.DataAccessException;
import dataAccess.*;

import model.AuthToken;
import model.Person;
import model.User;
import result.PersonResult;

import java.util.Objects;

public class PersonService extends Service {
    public PersonResult person(String authtoken, String personID) throws DataAccessException {
        openConnections();;
        try {
            AuthToken auth = adao.findByAuthtoken(authtoken);
            User user = udao.findByUsername(auth.getUsername());
            Person person = pdao.findById(personID);
            if(!Objects.equals(person.getUser_name(), user.getUser_name())) {
                System.out.println("The person with the given ID does not belong to the authtoken's User");
                closeConnections(false);
                return new PersonResult(false);
            }
            else {
                closeConnections(true);
                return new PersonResult(person.getUser_name(), person.getPerson_id(), person.getFirst_name(),
                        person.getLast_name(), person.getGender(), person.getFather_id(), person.getMother_id(),
                        person.getSpouse_id(), true);
            }
        } catch(DataAccessException ex) {
            System.out.println("There was no user found with the given authtoken");
            closeConnections(false);
            return new PersonResult(false);
        }
    }
}
