package result;

import model.Person;

import java.util.ArrayList;

public class PersonsResult extends Result {
    public boolean success;
    public Person[]data;

    public PersonsResult(ArrayList<Person>family) {
        this.data = family.toArray(new Person[0]);
        this.success = true;
    }

    public PersonsResult(boolean failure) {
        this.success = failure;
        this.message = "error";
    }

    public boolean isSuccess() {
        return success;
    }

    public Person[] getData() {
        return data;
    }
}
