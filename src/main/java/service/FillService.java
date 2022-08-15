package service;

import dataAccess.DataAccessException;
import dataAccess.EventDao;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import generator.HistoryGenerator;
import model.Event;
import model.Person;
import model.User;
import result.FillResult;

import java.io.IOException;

public class FillService extends Service {

    /**
     * This constructor will use PersonGenerator to populate the Person table with num_gen^2 new persons
     * associated to a specific username
     * @param username String username that we are going to fill in the events for
     * @param num_gen number of generations of ancestors we will generate
     * @return result object
     */
    public FillResult fill(String username, String num_gen) throws DataAccessException {
        System.out.println("Filling up "+num_gen+"-generation tree for user: "+ username);
        try {
            openConnections();
            User user = udao.findByUsername(username);

            pdao.clearUserPersons(user.getUser_name());
            edao.clearUserEvents(user.getUser_name());

            Person root = new Person(user.getPerson_id(), user.getUser_name(),
                    user.getFirst_name(), user.getLast_name(), user.getGender(), generateId(), generateId(),
                    generateId());
            HistoryGenerator hg = new HistoryGenerator(Integer.parseInt(num_gen), root);
            for(Person person: hg.getPersons()) {
                pdao.insertPerson(person);
            }
            for(Event event: hg.getEvents()) {
                edao.insertEvent(event);
            }
            closeConnections(true);
            return new FillResult(hg.getPersons().size());
        } catch(DataAccessException | IOException ex) {
            closeConnections(false);
            return new FillResult(false);
        }
    }
}
