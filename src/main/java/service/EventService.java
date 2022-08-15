package service;

import dataAccess.*;

import model.*;
import result.EventResult;

import java.util.Objects;

public class EventService extends Service {
    public EventService() {}

    public EventResult event(String authtoken, String eventID) throws DataAccessException {
        openConnections();
        try {
            AuthToken auth = adao.findByAuthtoken(authtoken);
            User user = udao.findByUsername(auth.getUsername());
            Event event = edao.findById(eventID);
            if(!Objects.equals(event.getUser_name(), user.getUser_name())) {
                closeConnections(false);
                return new EventResult(false, "The event with the given ID does not belong to the authtoken's User");
            }
            else {
                closeConnections(true);
                return new EventResult(event.getUser_name(), event.getEvent_id(), event.getPerson_id(),
                        event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(),
                        event.getEvent_type(), event.getYear(), true);
            }
        } catch(DataAccessException ex) {
            closeConnections(false);
            return new EventResult(false, "There was no user found with the given authtoken");
        }
    }
}
