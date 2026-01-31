import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Task {
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
        }
        else {
            front = "[ ]";
        }
        return front + " " + this.getName();
    }
}
class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
class Deadline extends Task {
    LocalDate deadline;
    public Deadline(String dl, String name) {
        super(name);
        try {
            deadline = LocalDate.parse(dl);
        } catch(DateTimeParseException e) {
            throw new IllegalArgumentException("Deadline needs to be in the format yyyy-mm-dd" + dl);
        }
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
class Event extends Task {
    String startTime;
    String endTime;
    public Event(String startTime, String endTime, String name) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + startTime + " to: " + endTime + ")";
    }
}
class EmptyDescException extends Exception {
    public EmptyDescException(String command) {
        super("Ermmm the description of " + command + " can't be blank la.");
    }
}
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
                }
                else if (command.equals("list")) {
                    for (int i = 0; i < list.size(); i++) {
                        Task curr = list.get(i);
                        System.out.println((i + 1) + "." + curr.toString());
                    }
                } else if(command.equals("unknown")) {
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
                } else if(command.equals("remove")) {
                    int numOfTask = Integer.parseInt(details);
                    Task taski = list.get(numOfTask - 1);
                    list.remove(numOfTask - 1);
                    storage.saveTasks(list);
                    System.out.println("Okay bubs I removed: " + taski.toString() + "Now you have " + list.size() + " tasks" +
                            " tasks in the list");

                } else {
                    System.out.println("Means?");
                }
            } catch (EmptyDescException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Alright, see you again.");
        System.out.println(line);
    }
}
