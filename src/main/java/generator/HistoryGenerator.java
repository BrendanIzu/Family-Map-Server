package generator;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryGenerator extends Generator {
    public Person person = new Person();
    public int num_gens;
    private final ArrayList<Person> persons = new ArrayList<>();
    public ArrayList<Event> events = new ArrayList<>();

    public HistoryGenerator(int num_gens, Person person) throws IOException {
        this.person = person;
        this.num_gens = num_gens;
        EventsGenerator eg = new EventsGenerator(person.getUser_name(), person.getPerson_id());
        int root_year = randBetween(1300, 2020);
        events.add(eg.generateBirth(person, root_year));
        generatePersonTree(this.num_gens, this.person, root_year);
    }

    private void generatePersonTree(int gens_left, Person curr_person, int curr_birth) {
        if(gens_left > 0) {
            persons.add(curr_person);
            PersonGenerator pg = new PersonGenerator();
            ArrayList<Person>parents = pg.generateParents(curr_person);

            int [] parents_birth = generateCoupleEvents(parents.get(0),parents.get(1), curr_birth);
            generatePersonTree(gens_left-1, parents.get(0), parents_birth[0]);
            generatePersonTree(gens_left-1, parents.get(1), parents_birth[1]);
        } else {
            // We needed to remove the father and mother
            Person last = new Person(
                    curr_person.getPerson_id(),
                    curr_person.getUser_name(),
                    curr_person.getFirst_name(),
                    curr_person.getLast_name(),
                    curr_person.getGender(),
                    null,
                    null,
                    curr_person.getSpouse_id()
            );
            persons.add(last);
        }
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public int[] generateCoupleEvents(Person father, Person mother, int child_birth) {
        EventsGenerator eg = new EventsGenerator();
        events.addAll(eg.generateCoupleEvents(father, mother, child_birth));
        // 0, and 3 contain the birth years of parents
        return new int[]{eg.getEvents().get(0).getYear(), eg.getEvents().get(3).getYear()};
    }
}
