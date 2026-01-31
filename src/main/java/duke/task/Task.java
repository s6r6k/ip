package duke.task;

public class Task {
    private boolean completed;
    private String name;
    private static int totalTasks;

    public Task(String name) {
        this.completed = false;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void complete() {
        this.completed = true;
    }

    public void unmark() {
        this.completed = false;
    }

    public String toString() {
        String front = "";
        if (this.isCompleted()) {
            front = "[X]";
        } else {
            front = "[ ]";
        }
        return front + " " + this.getName();
    }
}
