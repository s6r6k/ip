package duke.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    public LocalDate deadline;
    public Deadline(String dl, String name) {
        super(name);
        try {
            deadline = LocalDate.parse(dl);
        } catch(DateTimeParseException e) {
            throw new IllegalArgumentException("Deadline needs to be in the format yyyy-mm-dd" + dl);
        }
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
