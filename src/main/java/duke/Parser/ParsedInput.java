package duke.Parser;

/** this class is a container for user input, breaking it down to its command & details
 */
public class ParsedInput {
    private final String command;
    private final String details;

    public ParsedInput(String command, String details) {
        this.command = command;
        this.details = details;
    }

    public String getCommand() {
        return command;
    }

    public String getDetails() {
        return details;
    }
}
