package model;

public class User {

    /**
     * username
     */
    private String username;

    /**
     * user's password
     */
    private String password;

    /**
     * user's email
     */
    private String email;

    /**
     * user's first name
     */
    private String firstName;

    /**
     * user's last name
     */
    private String lastName;

    /**
     * user's gender
     */
    private String gender;

    /**
     * user's person id
     */
    private String personID;

    public String getUser_name() {
        return username;
    }

    public void setUser_name(String user_name) {
        this.username = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPerson_id() {
        return personID;
    }

    public void setPerson_id(String person_id) {
        this.personID = person_id;
    }

    /**
     * creates a person without a person_id
     *
     * @param user_name the username
     * @param password the user's password
     * @param email the user's email
     * @param first_name the user's first name
     * @param last_name the user's last name
     * @param gender the user's gender
     * @param person_id the user's person_id
     */
    public User(String user_name, String password, String email, String first_name, String last_name, String gender,
                String person_id) {
        setUser_name(user_name);
        setPassword(password);
        setEmail(email);
        setFirst_name(first_name);
        setLast_name(last_name);
        setGender(gender);
        setPerson_id(person_id);
    }

}