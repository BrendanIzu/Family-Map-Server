package result;

import request.LoadRequest;

public class LoadResult {
    public boolean success;
    String message;
    public LoadResult(int num_users, int num_persons, int num_events) {
        this.message = "Successfully added "+num_users+" users, "+num_persons+" persons, and "+
                num_events+" events to the database.";
        this.success = true;
    }

    public LoadResult() {
        this.message = "error";
        success = false;
    }
}
