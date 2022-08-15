package service;

import dataAccess.DataAccessException;
import dataAccess.*;
import model.*;
import request.LoadRequest;
import result.LoadResult;

public class LoadService extends Service {
    /**
     * returns a result based on the given request
     *
     * @param r request object
     * @return result object
     */
    public LoadResult load(LoadRequest r) throws DataAccessException {
        openConnections();
        clearData();

        try {
            for (User user : r.users) udao.insertUser(user);
            for (Person person : r.persons) pdao.insertPerson(person);
            for (Event event : r.events) edao.insertEvent(event);

            closeConnections(true);
            return new LoadResult(r.users.size(), r.persons.size(), r.events.size());
        } catch (DataAccessException ex) {
            System.out.println("Error occurred adding into the database");
            closeConnections(false);
            return new LoadResult();
        }
    }
}
