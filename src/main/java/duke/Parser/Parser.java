package duke.Parser;

import duke.Exception.EmptyDescException;
import duke.Parser.ParsedInput;

/** creates ParsedInput object out of user input
 * throws EmptyDescException if command is empty
 *return PI object with command as "unknown" if its unknown
 * rightfully declares exception
 */
public class Parser {
    public static ParsedInput parse(String input) throws EmptyDescException{
        input = input.trim();
        if (input.isEmpty()) {
            throw new EmptyDescException("command");
        }
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String details = parts.length > 1 ? parts[1].trim(): "";
        switch (command) {
        case "list":
        case "bye":
            return new ParsedInput(command, "");
        case "mark":
        case "unmark":
        case "todo":
        case "deadline":
        case "event":
        case "remove":
        case "find":
            if (details.isEmpty()) {
                throw new EmptyDescException(command);
            }
            return new ParsedInput(command, details);
        default:
            return new ParsedInput("unknown", input);
        }

    }
}

