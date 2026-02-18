package duke.Task;

import java.util.ArrayList;

/**
 * Represents an event task with a start and end time.
 * An event can also be checked against existing tasks for time clashes.
 */
public class Event extends Task {
    private String startTime;
    private String endTime;

    /**
     * Creates an Event with the specified time range and description.
     *
     * @param startTime Start time in HHMM format.
     * @param endTime End time in HHMM format.
     * @param name Description of the event.
     */
    public Event(String startTime, String endTime, String name) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time of the event.
     *
     * @return Start time as a string.
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * Returns the end time of the event.
     *
     * @return End time as a string.
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return Formatted event string with time range.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + startTime + " to: " + endTime + ")";
    }

    /**
     * Checks whether a new event clashes with any existing event in the task list.
     *
     * @param newEvent The event to be added.
     * @param taskList List of existing tasks.
     * @return True if there is a time clash, false otherwise.
     */
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
