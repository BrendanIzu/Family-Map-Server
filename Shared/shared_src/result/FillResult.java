package result;

public class FillResult {
    public boolean success;
    String message;
    public FillResult(){}
    public FillResult(int persons) {
        this.success = true;
        this.message = "Successfully added "+persons+" persons and "+((persons*3)-2)+" events to the database.";
     }
    public FillResult(boolean failure) {
        this.success = failure;
        this.message = "failed to fill";
    }
}
