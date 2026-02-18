package duke.Task;

import java.util.ArrayList;

/**
 * A container for Task objects that provides operations to manage the list.
 * This class handles low-level list manipulations to keep the main logic clean.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList with an initial list of tasks.
     *
     * @param initialTasks List of tasks loaded from storage.
     */
    public TaskList(ArrayList<Task> initialTasks) {
        this.tasks = initialTasks;
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @return Formatted task list or a message if the list is empty.
     */
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

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task at the specified index.
     *
     * @param index Index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        Task taski = tasks.get(index);
        tasks.remove(index);
        return taski;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Total number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index Index of the task.
     * @return Task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Checks if a new event clashes with any existing event in the list.
     *
     * @param newEvent Event to check.
     * @return True if there is a clash, false otherwise.
     */
    public boolean hasEventClash(Event newEvent) {
        int newStart = Integer.parseInt(newEvent.getStartTime());
        int newEnd = Integer.parseInt(newEvent.getEndTime());

        if (newStart >= newEnd) {
            return true;
        }

        for (Task t : tasks) {
            if (t instanceof Event) {
                Event existing = (Event) t;

                int existStart = Integer.parseInt(existing.getStartTime());
                int existEnd = Integer.parseInt(existing.getEndTime());

                if (newStart < existEnd && newEnd > existStart) {
                    return true;
                }
            }
        }
        return false;
    }
}
