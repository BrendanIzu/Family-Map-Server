package service;

import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Event;
import result.EventsResult;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventsService extends Service {
    public EventsResult history(String authtoken) {
        openConnections();
        if(validAuth(authtoken)) {
            ArrayList<Event> history = new ArrayList<>();
            try {
                AuthToken auth = adao.findByAuthtoken(authtoken);
                history = edao.getALL(auth.getUsername());
                closeConnections(true);
                return new EventsResult(history);
            } catch(DataAccessException | SQLException ex) {
                System.out.println("Error when getting ALL");
                closeConnections(false);
                return new EventsResult();
            }
        }
        else {
            System.out.println("BAD AUTH TOKEN");
            closeConnections(false);
            return new EventsResult();
        }
    }
}
