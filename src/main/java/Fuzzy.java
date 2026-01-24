import java.util.Scanner;
import java.util.ArrayList;
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
    String deadline;
    public Deadline(String dl, String name) {
        super(name);
        this.deadline = dl;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
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
        ArrayList<Task> list = new ArrayList<>();
        int totalTasks = 0;

        String line = "";
        for (int i = 0; i < 35; i++) {
            line += "_";
        }
        System.out.println(line);
        String greeting = "Hello I'm Fuzzy!" + "\n" + "What can I do for you babe?";
        System.out.println(greeting + "\n" + line);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String details = parts.length > 1? parts[1] : "";
        while (!command.equals("bye")) {
            try {
                if (input.equals("list")) {
                    for (int i = 0; i < list.size(); i++) {
                        Task curr = list.get(i);
                        System.out.println((i + 1) + "." + curr.toString());
                    }
                } else if(!(command.equals("mark") || command.equals("unmark") || command.equals("todo") || command.equals("deadline") || command.equals("event"))){
                    System.out.println("Means?");
                } else if (details.trim().equals("")) {
                    throw new EmptyDescException(command);
                } else if (command.equals("mark")) {
                    int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                    Task curry = list.get(taskNum - 1);
                    curry.complete();
                    System.out.println("I marked it, madam:" + "\n" + curry.toString());
                } else if (command.equals("unmark")) {
                    int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                    Task curry = list.get(taskNum - 1);
                    curry.unmark();
                    System.out.println("Ok, unmarked it bubs:" + "\n" + curry.toString());
                } else if (command.equals("todo")) {
                    totalTasks++;
                    ToDo td = new ToDo(details);
                    list.add(td);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + totalTasks + "." + td.toString() + "\n"
                            + "You have " + totalTasks + " tasks darling");
                    System.out.println(line);
                } else if (command.equals("deadline")) {
                    totalTasks++;
                    String by = details.split("/by", 2)[1].trim();
                    String deets = details.split("/by", 2)[0].trim();
                    Deadline dl = new Deadline(by, deets);
                    list.add(dl);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + totalTasks + "." + dl.toString() + "\n"
                            + "You have " + totalTasks + " tasks darling");
                    System.out.println(line);

                } else if (command.equals("event")) {
                    totalTasks++;
                    String afterFrom = details.split("/from", 2)[1];
                    String beforeFrom = details.split("/from", 2)[0];
                    String start = afterFrom.split("/to", 2)[0];
                    String to = afterFrom.split("/to", 2)[1];
                    Event ev = new Event(start, to, beforeFrom);
                    list.add(ev);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + totalTasks + "." + ev.toString() + "\n"
                            + "You have " + totalTasks + " tasks darling");
                    System.out.println(line);
                } else {
                    System.out.println("Means?");
                }
            } catch (EmptyDescException e){
                System.out.println(e.getMessage());
            }
            input = scanner.nextLine();
            parts = input.split(" ", 2);
            command = parts[0];
            details = parts.length > 1 ? parts[1] : "";
        }
        System.out.println("Alright, see you again.");
        scanner.close();
        System.out.println(line);
    }
}
