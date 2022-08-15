package result;

public class EventResult {
    public boolean success;
    String associatedUsername;
    String eventID;
    String personID;
    Double latitude;
    Double longitude;
    String country;
    String city;
    String eventType;
    int year;
    String message;
    public EventResult() {}
    public EventResult(String associatedUsername, String eventID, String personID, Double latitude, Double longitude,
                       String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }
    public EventResult(boolean failure, String message) {
        this.message = "error: "+message;
        this.success = failure;
    }

    public String getEventID() {
        return eventID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public String getMessage() {
        return message;
    }
}
