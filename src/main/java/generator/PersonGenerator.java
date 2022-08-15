package generator;

import com.google.gson.Gson;
import model.*;
import java.io.*;
import java.util.ArrayList;

public class PersonGenerator extends Generator {
    public Names mnames;
    public Names fnames;
    public Names snames;
    private final ArrayList<Person>persons = new ArrayList<>();
    public PersonGenerator() {
        try {
            grabNamesFromJson();
        } catch(IOException ex) {
            System.out.println("ERROR occurred in reading names from json");
        }
    }

    /**
     * This will grab all the male, female and surnames from the json files
     * @throws IOException ex
     */
    private void grabNamesFromJson() throws IOException {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("json/mnames.json");
            mnames = gson.fromJson(reader, Names.class);
            reader.close();

            reader = new FileReader("json/fnames.json");
            fnames = gson.fromJson(reader, Names.class);
            reader.close();

            reader = new FileReader("json/snames.json");
            snames = gson.fromJson(reader, Names.class);
            reader.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public ArrayList<Person> generateParents(Person person) {
        ArrayList<Person> persons = new ArrayList<>();
        String father_name = randMName();
        String mother_name = randFName();

        // FATHER
        Person father = new Person(
                person.getFather_id(),
                person.getUser_name(),
                father_name,
                person.getLast_name(),
                "m",
                generateId(),
                generateId(),
                person.getMother_id()
        );
        // MOTHER
        Person mother = new Person(
                person.getMother_id(),
                person.getUser_name(),
                mother_name,
                randSName(),
                "f",
                generateId(),
                generateId(),
                person.getFather_id()
        );
        persons.add(father);
        persons.add(mother);
        return persons;
    }

    /**
     * @return random male name
     */
    private String randMName(){
        return mnames.data[randBetween(0, mnames.data.length-1)];
    }

    /**
     * @return random female name
     */
    private String randFName(){
        return fnames.data[randBetween(0, fnames.data.length-1)];
    }

    /**
     * @return random surname name
     */
    private String randSName(){
        // TODO: NOTE: all couples will have the same surname, if a woman is married then her parents will have a
        //  different surname than her
        return snames.data[randBetween(0, snames.data.length-1)];
    }
}
