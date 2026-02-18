package duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FuzzyTest {

    @Test
    public void getResponse_invalidTaskNumber_returnsErrorMessage() {
        Fuzzy fuzzy = new Fuzzy();
        String response = fuzzy.getResponse("mark abc");
        assertTrue(response.contains("valid number")
                || response.contains("wrong"));
    }
}
