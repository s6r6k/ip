package duke.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a specific deadline.
 * Uses LocalDate for parsing and formatting date values.
 */
public class Deadline extends Task {
    private static final String OUTPUT_DATE_FORMAT = "MMM dd yyyy";
    private static final String INPUT_DATE_FORMAT_HINT = "yyyy-mm-dd";

    private final LocalDate deadlineDate;

    /**
     * Creates a Deadline task with the given description and date.
     *
     * @param dateString Deadline date in yyyy-mm-dd format.
     * @param description Description of the task.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    public Deadline(String dateString, String description) {
        super(description);
        assert description != null : "Task description should not be null";
        assert dateString != null : "Deadline string should not be null";

        try {
            this.deadlineDate = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Deadline must be in " + INPUT_DATE_FORMAT_HINT + " format. Received: " + dateString);
        }
    }

    /**
     * Returns the deadline date of this task.
     *
     * @return Deadline as a LocalDate.
     */
    public LocalDate getDeadline() {
        return this.deadlineDate;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return Formatted task string with deadline.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);
        return "[D]" + super.toString() + " (by: " + deadlineDate.format(formatter) + ")";
    }
}
