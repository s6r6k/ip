package duke.Task;

import duke.Task.Task;
import java.util.ArrayList;

/**
 * A container for Task objects that provides operations to manage the list.
 * This class handles low-level list manipulations to keep the main logic clean.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> initialTasks) {
        this.tasks = initialTasks;
    }

    public String getFormattedTasks() {
        if (this.tasks.isEmpty()) {
            return "Your list is empty, darling!";
        }

        StringBuilder summaryBuilder = new StringBuilder("Here are your tasks, darling:\n");
        for (int i = 0; i < this.tasks.size(); i++) {
            Task currentTask = this.tasks.get(i);
            summaryBuilder.append((i + 1))
                    .append(".")
                    .append(currentTask.toString())
                    .append("\n");
        }
        return summaryBuilder.toString().trim();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }
}