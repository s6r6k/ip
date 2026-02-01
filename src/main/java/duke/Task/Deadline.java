package duke.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    LocalDate deadline;
    public Deadline(String dl, String name) {
        super(name);
        try {
            deadline = LocalDate.parse(dl);
        } catch(DateTimeParseException e) {
            throw new IllegalArgumentException("Deadline needs to be in the format yyyy-mm-dd" + dl);
        }
    }
    public LocalDate getDeadline() {
        return this.deadline;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}