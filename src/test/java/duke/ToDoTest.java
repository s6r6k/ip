package duke;
import duke.Task.ToDo;
import org.junit.jupiter.api.Test; //Marks a method as a test case
import static org.junit.jupiter.api.Assertions.*; //assertEquals etc

//structure of every JUnit test:
//AAA: Arrange, set up objects, Act, call the method, Assert-check the result
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

//JUNIT NOTES:
//JUNIT LOOKS FOR CLASSES THAT END WITH TEST AND CHEKC THE METHODS FOR @Test
//JUNIT will control when this method runs etc
//so now we are checking if isCompleted is really true like it should be, test passed.
//for 2nd method tdo object should not have been completed so its false.