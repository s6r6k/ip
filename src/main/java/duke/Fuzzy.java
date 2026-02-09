package duke;
import duke.Exception.EmptyDescException;
import duke.Parser.ParsedInput;
import duke.Parser.Parser;
import duke.Task.*;
import duke.Storage.Storage;
import duke.Ui.Ui;

public class Fuzzy {
    private Storage storage;
    private TaskList list;
    private Ui ui;

    public Fuzzy() {
        storage = new Storage("./data/fuzzy.txt");
        list = new TaskList(storage.loadTasks());
        ui = new Ui();
    }

    /**
     * This method replaces the 'while' loop.
     * It takes the user's input and returns Fuzzy's response.
     */
    public String getResponse(String input) {
        try {
            ParsedInput pi = Parser.parse(input);
            String command = pi.getCommand();
            String details = pi.getDetails();

            if (command.equals("bye")) {
                return "Alright, see you again.";
            } else if (command.equals("list")) {
                StringBuilder sb = new StringBuilder("Here are your tasks, darling:\n");
                for (int i = 0; i < list.size(); i++) {
                    sb.append((i + 1)).append(".").append(list.get(i).toString()).append("\n");
                }
                return sb.toString();
            } else if (command.equals("mark")) {
                int taskNum = Integer.parseInt(details);
                Task curry = list.get(taskNum - 1);
                curry.complete();
                storage.saveTasks(list);
                return "I marked it, madam:\n" + curry.toString();
            } else if (command.equals("todo")) {
                ToDo td = new ToDo(details);
                list.add(td);
                storage.saveTasks(list);
                return "Alrighty, added it:\n" + td.toString() + "\nYou have " + list.size() + " tasks darling";
            }
            // Add your other if-else logic here (deadline, event, etc.)
            // but return Strings instead of System.out.println

            return "Means?";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}