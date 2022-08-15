package generator;

public class Place {
    public String country;
    public String city;
    public Double latitude;
    public Double longitude;

    public Place(){}
    public Place(String country, String city, Double latitude, Double longitude){
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        return city+", "+country;
    }
}
