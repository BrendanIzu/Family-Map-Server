package generator;

public class Names {
    public String[] data;
    public Names() {}
    public Names(String[] data){
        this.data = data;
    }
    public void printThisMany(int num_names) {
        for(int i = 0; i<num_names; i++) {
            System.out.println(data[i]);
        }
    }
}
