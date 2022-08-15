package result;

import model.Event;
import java.util.ArrayList;

public class EventsResult extends Result {
    public boolean success;
    public Event[]data;

    public EventsResult(ArrayList<Event>history) {
        this.data = history.toArray(new Event[0]);
        this.success = true;
    }
    public EventsResult() {
        this.success = false;
        this.message = "error [description of error]";
    }

    public boolean isSuccess() {
        return success;
    }

    public Event[] getData() {
        return data;
    }
}
