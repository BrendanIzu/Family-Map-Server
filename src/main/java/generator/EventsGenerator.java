package generator;

import com.google.gson.Gson;
import model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventsGenerator extends Generator {
    private ArrayList<Event> events = new ArrayList<>();
    private Locations locations;

    public EventsGenerator() {
        try {
            grabLocationsFromJson();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public EventsGenerator(String username, String personID) throws IOException {
        try {
            grabLocationsFromJson();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void grabLocationsFromJson() throws IOException {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("json/locations.json");
            locations = gson.fromJson(reader, Locations.class);
            reader.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Place randPlace() {
        return locations.data[randBetween(0, locations.data.length-1)];
    }

    public Event generateBirth(Person person, int birth_year) {
        Place place = randPlace();
        return new Event(generateId(), person.getUser_name(), person.getPerson_id(), place.latitude, place.longitude, place.country, place.city,
                "Birth", birth_year);
    }
    public ArrayList<Event> generateCoupleEvents(Person father, Person mother, int child_birth) {
        String username = father.getUser_name();

        // Mother - birth
        int m_birth_year = randBetween(child_birth-50, child_birth-20);
        Place m_birth_place = randPlace();
        Event m_birth = new Event(generateId(), username, mother.getPerson_id(), m_birth_place.latitude,
                m_birth_place.longitude, m_birth_place.country, m_birth_place.city, "Birth", m_birth_year);

        // Father - birth
        Place f_birth_place = randPlace();
        int f_birth_year = randBetween(m_birth_year-2, m_birth_year+2);
        Event f_birth = new Event(generateId(), username, father.getPerson_id(), f_birth_place.latitude,
                f_birth_place.longitude, f_birth_place.country, f_birth_place.city, "Birth", f_birth_year);

        // Marriage
        int marr_year = randBetween(m_birth_year+18, m_birth_year+50);
        Place marr_place = randPlace();
        Event f_marr = new Event(generateId(), username, father.getPerson_id(), marr_place.latitude,
                marr_place.longitude, marr_place.country, marr_place.city, "Marriage", marr_year);
        Event m_marr = new Event(generateId(), username, mother.getPerson_id(), marr_place.latitude,
                marr_place.longitude, marr_place.country, marr_place.city, "Marriage", marr_year);

        // Mother - death
        int m_death_year = randBetween(child_birth+20, m_birth_year+120);
        Place m_death_place = randPlace();
        Event m_death= new Event(generateId(), username, mother.getPerson_id(), m_death_place.latitude,
        m_death_place.longitude, m_death_place.country, m_death_place.city, "Death", m_death_year);

        // Father - death
        int f_death_year = randBetween(child_birth+20, f_birth_year+120);
        Place f_death_place = randPlace();
        Event f_death= new Event(generateId(), username, father.getPerson_id(), f_death_place.latitude,
                f_death_place.longitude, f_death_place.country, f_death_place.city, "Death", f_death_year);

        events.add(f_birth); //0
        events.add(f_marr);
        events.add(f_death);

        events.add(m_birth); //3
        events.add(m_marr);
        events.add(m_death);

        return events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
