package duke.Parser;

/**
 * Represents the result of parsing user input.
 * This class acts as a container for the command type and its associated details.
 */
public class ParsedInput {
    private final Parser.CommandType commandType;
    private final String argumentDetails;

    public ParsedInput(Parser.CommandType commandType, String argumentDetails) {
        this.commandType = commandType;
        this.argumentDetails = argumentDetails;
    }
    public Parser.CommandType getCommandType() {
        return commandType;
    }
    public String getDetails() {
        return argumentDetails;
    }
}