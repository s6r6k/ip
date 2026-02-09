package duke;
import duke.Exception.EmptyDescException;
import duke.Parser.ParsedInput;
import duke.Parser.Parser;
import duke.Storage.Storage;
import duke.Task.*;
import duke.Ui.Ui;

/**
 * Incomplete in terms of class extracting
 */
public class Fuzzy {
    public static void main(String[] args) {
        Storage storage = new Storage("./data/fuzzy.txt"); //you cant access instance lvl field outside static class
        TaskList list = new TaskList(storage.loadTasks());

        String line = "";
        for (int i = 0; i < 35; i++) {
            line += "_";
        }
        Ui ui = new Ui();
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand(); //anything that can throw error should be in try
                ParsedInput pi = Parser.parse(input);
                String command = pi.getCommand();
                String details = pi.getDetails();

                if (command.equals("bye")) {
                    isExit = true;
                    continue;
                } else if (command.equals("list")) {
                    for (int i = 0; i < list.size(); i++) {
                        Task curr = list.get(i);
                        System.out.println((i + 1) + "." + curr.toString());
                    }
                } else if (command.equals("unknown")) {
                    System.out.println("Means?");
                } else if (command.equals("mark")) {
                    int taskNum = Integer.parseInt(details);
                    Task curry = list.get(taskNum - 1);
                    curry.complete();
                    storage.saveTasks(list);
                    System.out.println("I marked it, madam:" + "\n" + curry.toString());
                } else if (command.equals("unmark")) {
                    int taskNum = Integer.parseInt(details);
                    Task curry = list.get(taskNum - 1);
                    curry.unmark();
                    storage.saveTasks(list);
                    System.out.println("Ok, unmarked it bubs:" + "\n" + curry.toString());
                } else if (command.equals("todo")) {
                    ToDo td = new ToDo(details);
                    list.add(td);
                    storage.saveTasks(list);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + list.size() + "." + td.toString() + "\n"
                            + "You have " + list.size() + " tasks darling");
                    System.out.println(line);
                } else if (command.equals("deadline")) {
                    if (!details.contains("/by")) {
                        throw new IllegalArgumentException("Deadline must have /by");
                    }
                    String by = details.split("/by", 2)[1].trim();
                    String deets = details.split("/by", 2)[0].trim();
                    Deadline dl = new Deadline(by, deets);
                    list.add(dl);
                    storage.saveTasks(list);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + list.size() + "." + dl.toString() + "\n"
                            + "You have " + list.size() + " tasks darling");
                    System.out.println(line);

                } else if (command.equals("event")) {
                    String afterFrom = details.split("/from", 2)[1];
                    String beforeFrom = details.split("/from", 2)[0].trim();
                    String start = afterFrom.split("/to", 2)[0];
                    String to = afterFrom.split("/to", 2)[1];
                    Event ev = new Event(start, to, beforeFrom);
                    list.add(ev);
                    storage.saveTasks(list);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + list.size() + "." + ev.toString() + "\n"
                            + "You have " + list.size() + " tasks darling");
                    System.out.println(line);
                } else if (command.equals("remove")) {
                    int numOfTask = Integer.parseInt(details);
                    Task taski = list.get(numOfTask - 1);
                    list.remove(numOfTask - 1);
                    storage.saveTasks(list);
                    System.out.println("Okay bubs I removed: " + taski.toString() + "Now you have "
                            + list.size() + " tasks" + " tasks in the list");

                } else if (command.equals("find")) {
                    String keyword = details;
                    ui.showLine();
                    System.out.println("These match your request, Ma'am:");
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) {
                        Task task = list.get(i);
                        if (task.getName().contains(keyword)) {
                            count++;
                            System.out.println(count + "." + task.toString());
                        }
                    }
                    ui.showLine();
                } else {
                    System.out.println("Means?");
                }
            } catch (EmptyDescException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Alright, see you again.");
        System.out.println(line);
    }
}
