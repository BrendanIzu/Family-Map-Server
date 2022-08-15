package result;

public class ClearResult {
    public boolean success;
    String message;
    public ClearResult(boolean success) {
        if(!success) {
            this.success = false;
            this.message = "error";
        }
        else {
            this.success = true;
            this.message = "clear succeeded";
        }
    }
}
