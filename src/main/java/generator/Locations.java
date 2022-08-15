package generator;

public class Locations {
    public Place[] data;
    public Locations(){}
    public Locations(Place[]data) {
        this.data = data;
    }
    public void printThisMany(int num_locations) {
        for(int i=0; i<num_locations; i++) {
            System.out.println(data[i].toString());
        }
    }
}
