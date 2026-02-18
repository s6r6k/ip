package duke.Storage;

import duke.Task.Task;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @Test
    public void loadTasks_fileDoesNotExist_returnsEmptyList() {
        Storage storage = new Storage("non_existent_file.txt");
        ArrayList<Task> tasks = storage.loadTasks();
        assertTrue(tasks.isEmpty());
    }
}
