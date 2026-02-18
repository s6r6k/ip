package duke.Parser;

import duke.Exception.EmptyDescException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_validTodoWithSpaces_success() throws EmptyDescException {
        ParsedInput result = Parser.parse("  todo read book");
        assertEquals(Parser.CommandType.TODO, result.getCommandType());
        assertEquals("read book", result.getDetails());
    }

    @Test
    public void parse_listCommand_success() throws EmptyDescException {
        ParsedInput result = Parser.parse("list");
        assertEquals(Parser.CommandType.LIST, result.getCommandType());
        assertEquals("", result.getDetails());
    }

    @Test
    public void parse_missingDetails_throwsException() {
        assertThrows(EmptyDescException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_byeCommand_success() throws EmptyDescException {
        ParsedInput result = Parser.parse("bye");
        assertEquals(Parser.CommandType.BYE, result.getCommandType());
        assertEquals("", result.getDetails());
    }

    @Test
    public void parse_deadlineCommand_success() throws EmptyDescException {
        ParsedInput result = Parser.parse("deadline submit report /by 2005-01-16");
        assertEquals(Parser.CommandType.DEADLINE, result.getCommandType());
        assertEquals("submit report /by 2005-01-16", result.getDetails());
    }

    @Test
    public void parse_unknownCommand_returnsUnknown() throws EmptyDescException {
        ParsedInput result = Parser.parse("blahblah something random");
        assertEquals(Parser.CommandType.UNKNOWN, result.getCommandType());
        assertEquals("blahblah something random", result.getDetails());
    }

    @Test
    public void parse_emptyInput_throwsException() {
        assertThrows(EmptyDescException.class, () -> Parser.parse(""));
    }
}
