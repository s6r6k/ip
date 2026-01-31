package duke.parser;
import duke.exception.EmptyDescException;

public class Parser {
    public static ParsedInput parse(String input) throws EmptyDescException {
        input = input.trim();
        if(input.isEmpty()) {
            throw new EmptyDescException("command");
        }
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String details = parts.length > 1? parts[1].trim(): "";
        switch(command) {
            case "list":
            case "bye":
                return new ParsedInput(command, "");
            case "mark":
            case "unmark":
            case "todo":
            case "deadline":
            case "event":
            case "remove":
                if(details.isEmpty()) {
                    throw new EmptyDescException(command);
                }
                return new ParsedInput(command, details);
            default:
                return new ParsedInput("unknown", input);
        }

    }

    public static class ParsedInput {
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
}
