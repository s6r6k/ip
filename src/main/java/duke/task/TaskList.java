package duke.task;
import java.util.ArrayList;
import duke.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> taski) {
        this.tasks = taski;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int i) {
        tasks.remove(i);
    }

    public int size() {
        return tasks.size();
    }
    public Task get(int i) {
        return tasks.get(i);
    }
}
