package duke.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a specific deadline.
 * This class uses LocalDate to handle date parsing and formatting.
 */
public class Deadline extends Task {
    private static final String OUTPUT_DATE_FORMAT = "MMM dd yyyy";
    private static final String INPUT_DATE_FORMAT_HINT = "yyyy-mm-dd";

    private final LocalDate deadlineDate;

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

    public LocalDate getDeadline() {
        return this.deadlineDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);
        return "[D]" + super.toString() + " (by: " + deadlineDate.format(formatter) + ")";
    }
}