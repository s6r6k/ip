package duke;
import duke.Task.ToDo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {
    @Test
    public void markAsDone() {
        ToDo todo = new ToDo("read book"); //arrange
        todo.complete(); //act
        assertTrue(todo.isCompleted()); //assert
    }

    @Test
    public void newToDo() {
        ToDo todo = new ToDo("sleep");
        assertFalse(todo.isCompleted());
    }
}
