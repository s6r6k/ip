package duke.Task;

import java.util.ArrayList;

public class Event extends Task {
    private String startTime;
    private String endTime;
    public Event(String startTime, String endTime, String name) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + startTime + " to: " + endTime + ")";
    }

    public static boolean hasEventClash(Event newEvent, ArrayList<Task> taskList) {
        for (Task t : taskList) {

            if (t instanceof Event) {
                Event existing = (Event) t;
                    if (Integer.parseInt(newEvent.startTime) < Integer.parseInt(existing.endTime) &&
                            Integer.parseInt(newEvent.endTime) > Integer.parseInt(existing.startTime)) {

                        System.out.println("Clash with event: " + existing.getName());
                        return true;
                    }
            }
        }
        return false;
    }
}