package result;

public class PersonResult {
    public boolean success;
    String associatedUsername;
    String personID;
    String firstName;
    String lastName;
    String gender;
    String fatherID;
    String motherID;
    String spouseID;
    String message;

    public PersonResult() {}

    public PersonResult(String associatedUsername, String personID, String firstName, String lastName,
                        String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    public PersonResult(boolean failure) {
        this.message = "error";
        this.success = failure;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public String getMessage() {
        return message;
    }
}
