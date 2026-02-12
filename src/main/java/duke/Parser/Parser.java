package duke.Parser;

import duke.Exception.EmptyDescException;

/**
 * Creates ParsedInput object out of user input.
 * High-level logic handles the transformation from String to CommandType.
 */
public class Parser {
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, REMOVE, FIND, UNKNOWN
    }

    public static ParsedInput parse(String input) throws EmptyDescException {
        if (input.isEmpty()) {
            throw new EmptyDescException("command");
        }

        String trimmedInput = input.trim(); // Using explanatory name
        assert !trimmedInput.isEmpty() : "Input is empty, yaar!";

        String[] parts = trimmedInput.split(" ", 2);
        String commandWord = parts[0];
        String details = parts.length > 1 ? parts[1].trim() : "";

        CommandType type = getCommandType(commandWord);

        if (type == CommandType.UNKNOWN) {
            return new ParsedInput(type, trimmedInput);
        }

        if (requiresDetails(type) && details.isEmpty()) {
            throw new EmptyDescException(commandWord);
        }

        return new ParsedInput(type, details);
    }
    private static CommandType getCommandType(String commandWord) {
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }
    private static boolean requiresDetails(CommandType type) {
        return !(type == CommandType.LIST || type == CommandType.BYE);
    }
}
