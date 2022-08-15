package model;

public class Person {
    /**
     * a person's id
     */
    private String personID;

    /**
     * a person's user name
     */
    private String associatedUsername;

    /**
     * a person's first name
     */
    private String firstName;

    /**
     * a person's last name
     */
    private String lastName;

    /**
     * a person's gender
     */
    private String gender;

    /**
     * a person's father's id
     */
    private String fatherID;

    /**
     * a person's mother's id
     */
    private String motherID;

    /**
     * a person's spouse's id
     */
    private String spouseID;

    public String getPerson_id() {
        return personID;
    }

    public void setPerson_id(String person_id) {
        this.personID = person_id;
    }

    public String getUser_name() {
        return associatedUsername;
    }

    public void setUser_name(String username) {
        this.associatedUsername = username;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather_id() {
        return fatherID;
    }

    public void setFather_id(String father_id) {
        this.fatherID = father_id;
    }

    public String getMother_id() {
        return motherID;
    }

    public void setMother_id(String mother_id) {
        this.motherID = mother_id;
    }

    public String getSpouse_id() {
        return spouseID;
    }

    public void setSpouse_id(String spouse_id) {
        this.spouseID = spouse_id;
    }

    public Person(){}

    /**
     * creates new person object
     *
     * @param person_id person's id
     * @param username person's username
     * @param first_name person's first name
     * @param last_name person's last name
     * @param gender person's gender
     * @param person_id person's id
     * @param father_id person's father's id
     * @param mother_id person's mother's id
     * @param spouse_id person's spouse's id
     */
    public Person(String person_id, String username, String first_name, String last_name, String gender,
                  String father_id, String mother_id, String spouse_id) {
        setPerson_id(person_id);
        setUser_name(username);
        setFirst_name(first_name);
        setLast_name(last_name);
        setGender(gender);
        setPerson_id(person_id);
        setFather_id(father_id);
        setMother_id(mother_id);
        setSpouse_id(spouse_id);
    }
}
