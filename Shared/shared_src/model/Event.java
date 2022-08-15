package model;

public class Event {
    /**
     * an event's id
     */
    private String eventID;

    /**
     * an event's username
     */
    private String associatedUsername;

    /**
     * an event's person's id
     */
    private String personID;

    /**
     * an event's latitude
     */
    private Double latitude;

    /**
     * an event's longitude
     */
    private Double longitude;

    /**
     * an event's country
     */
    private String country;

    /**
     * an event's city
     */
    private String city;

    /**
     * an event's event type
     */
    private String eventType;

    /**
     * an event's year
     */
    private int year;

    public String getEvent_id() {
        return eventID;
    }

    public void setEvent_id(String event_id) {
        this.eventID = event_id;
    }

    public String getUser_name() {
        return associatedUsername;
    }

    public void setUser_name(String user_name) {
        this.associatedUsername = user_name;
    }

    public String getPerson_id() {
        return personID;
    }

    public void setPerson_id(String person_id) {
        this.personID = person_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEvent_type() {
        return eventType;
    }

    public void setEvent_type(String event_type) {
        this.eventType = event_type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * creates new event object
     *  @param event_id event's
     * @param user_name username attached to event
     * @param person_id person's id who is attached to event
     * @param latitude event's latitude
     * @param longitude event's longitude
     * @param country event's country
     * @param city event's city
     * @param event_type event's type
     * @param year event's year
     */
    public Event(String event_id, String user_name, String person_id, Double latitude, Double longitude, String country,
                 String city, String event_type, Integer year) {
        setEvent_id(event_id);
        setUser_name(user_name);
        setPerson_id(person_id);
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
        setEvent_type(event_type);
        setYear(year);
    }
}
