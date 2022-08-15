package service;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.Person;
import result.PersonsResult;

import java.sql.SQLException;
import java.util.ArrayList;

public class PersonsService extends Service {
    public PersonsResult family(String authtoken) {
        openConnections();
        if(validAuth(authtoken)) {
            ArrayList<Person>family = new ArrayList<>();
            try {
                AuthToken auth = adao.findByAuthtoken(authtoken);
                family = pdao.getALL(auth.getUsername());
                closeConnections(true);
                return new PersonsResult(family);
            } catch(DataAccessException | SQLException ex) {
                System.out.println("Error when getting ALL");
                closeConnections(false);
                return new PersonsResult(false);
            }
        }
        else {
            System.out.println("BAD AUTH TOKEN");
            closeConnections(false);
            return new PersonsResult(false);
        }
    }
}
