package duke.Parser;
import duke.Exception.EmptyDescException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void parserDeetsTest() throws EmptyDescException {
        ParsedInput result = Parser.parse("  todo read book");
        assertEquals("todo", result.getCommand());
        assertEquals("read book", result.getDetails());
    }

    @Test
    public void parseList() throws EmptyDescException {
        ParsedInput result = Parser.parse("list");
        assertEquals("list", result.getCommand());
        assertEquals("", result.getDetails());
    }

    @Test
    public void parseException() {
        assertThrows(EmptyDescException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parseBye() throws EmptyDescException {
        ParsedInput result = Parser.parse("bye");
        assertEquals("bye", result.getCommand());
        assertEquals("", result.getDetails());
    }

    @Test
    public void parseDeadline() throws EmptyDescException {
        ParsedInput result = Parser.parse("deadline submit report /by 2005-01-16");
        assertEquals("deadline", result.getCommand());
        assertEquals("submit report /by 2005-01-16", result.getDetails());
    }
}

//valid input, parsers properly
//invalid input, must handle correctly
