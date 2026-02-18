package duke.Storage;

import duke.Task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saving and loading of tasks to and from a file.
 * Ensures task data persists between program runs.
 */
public class Storage {

    private final String filePath;

    /**
     * Creates a Storage instance for the given file path.
     *
     * @param filePath Path of the file where tasks will be saved and loaded.
     */
    public Storage(String filePath) {
        assert filePath != null : "Filepath shld not be null";
        this.filePath = filePath;
    }

    /**
     * Saves all tasks in the given TaskList to the file.
     * Creates the "./data" folder if it does not exist.
     *
     * @param list The TaskList containing tasks to save.
     */
    public void saveTasks(TaskList list) {
        assert list != null : "TaskList should not be null";
        try {
            File folder = new File("./data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            FileWriter fw = new FileWriter(filePath);

            for (int i = 0; i < list.getSize(); i++) {
                Task taski = list.get(i);
                assert taski != null : "Task at index " + i + " should not be null baby";
                String type = "";
                String extra = "";

                if (taski instanceof ToDo) {
                    type = "T";
                } else if (taski instanceof Deadline) {
                    type = "D";
                    extra = ((Deadline) taski).getDeadline().toString();
                } else if (taski instanceof Event) {
                    type = "E";
                    extra = ((Event) taski).getStartTime() + "-" + ((Event) taski).getEndTime();
                }

                int complete = taski.isCompleted() ? 1 : 0;

                if (extra.equals("")) {
                    fw.write(type + " | " + complete + " | " + taski.getName() + "\n");
                } else {
                    fw.write(type + " | " + complete + " | " + taski.getName()
                            + " | " + extra + "\n");
                }
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Yaar there was an error in saving: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return An ArrayList of Tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> list = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return list;
        }

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0];
                boolean done = parts[1].equals("1");
                String name = parts[2];
                Task task = null;

                switch (type) {
                case "T":
                    task = new ToDo(name);
                    break;
                case "D":
                    if (parts.length < 4) continue;
                    task = new Deadline(parts[3], name);
                    break;
                case "E":
                    if (parts.length < 4) continue;
                    String[] times = parts[3].split("-");
                    if (times.length < 2) continue;
                    task = new Event(times[0], times[1], name);
                    break;
                }

                if (task != null && done) {
                    task.complete();
                }
                if (task != null) {
                    list.add(task);
                }
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return list;
    }
}
