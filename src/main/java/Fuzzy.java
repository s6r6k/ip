import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    public static void saveTasks(ArrayList<Task> list) {
        try {
            File folder = new File("./data");
            if (!folder.exists()) folder.mkdir();
            File file = new File("./data/fuzzy.txt");
            FileWriter fw = new FileWriter("./data/fuzzy.txt");
            for(Task taski : list) {
                String type = "";
                String extra = "";
                if(taski instanceof ToDo) type = "T";
                else if(taski instanceof Deadline) {
                    type = "D";
                    extra = ((Deadline) taski).deadline;
                }
                else if(taski instanceof Event) {
                    type = "E";
                    extra = ((Event) taski).startTime + "-" + ((Event) taski).endTime;
                }
                int complete = taski.isCompleted()? 1:0;
                if(extra.equals("")) fw.write(type + " | " + complete + " | " + taski.getName() + "\n");
                else {
                    fw.write(type + " | " + complete + " | " + taski.getName() + " | " + extra + "\n");
                }
            }
            fw.close();
        } catch(IOException e) {
            System.out.println("Yaar there was an error in saving: " + e.getMessage());
        }
    }

    public static ArrayList<Task> taskLoader() {
        ArrayList<Task> list = new ArrayList<>();
        File file = new File("./data/fuzzy.txt");
        if (!file.exists()) return list;
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" \\| ");
                if(parts.length < 3) continue;
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

                if (task != null && done) task.complete();
                if (task != null) list.add(task);
            }
            sc.close();
            } catch(IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return list;
    }
    public static void main(String[] args) {
        ArrayList<Task> list = taskLoader();

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
                } else if(!(command.equals("mark") || command.equals("unmark") || command.equals("todo") || command.equals("deadline") || command.equals("event")
                || command.equals("remove"))){
                    System.out.println("Means?");
                } else if (details.trim().equals("")) {
                    throw new EmptyDescException(command);
                } else if (command.equals("mark")) {
                    int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                    Task curry = list.get(taskNum - 1);
                    curry.complete();
                    saveTasks(list);
                    System.out.println("I marked it, madam:" + "\n" + curry.toString());
                } else if (command.equals("unmark")) {
                    int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                    Task curry = list.get(taskNum - 1);
                    curry.unmark();
                    saveTasks(list);
                    System.out.println("Ok, unmarked it bubs:" + "\n" + curry.toString());
                } else if (command.equals("todo")) {
                    ToDo td = new ToDo(details);
                    list.add(td);
                    saveTasks(list);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + list.size() + "." + td.toString() + "\n"
                            + "You have " + list.size() + " tasks darling");
                    System.out.println(line);
                } else if (command.equals("deadline")) {
                    String by = details.split("/by", 2)[1].trim();
                    String deets = details.split("/by", 2)[0].trim();
                    Deadline dl = new Deadline(by, deets);
                    list.add(dl);
                    saveTasks(list);
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
                    saveTasks(list);
                    System.out.println(line);
                    System.out.println("Alrighty, added it:" + "\n" + list.size() + "." + ev.toString() + "\n"
                            + "You have " + list.size() + " tasks darling");
                    System.out.println(line);
                } else if(command.equals("remove")) {
                    int numOfTask = Integer.parseInt(details.trim());
                    Task taski = list.get(numOfTask - 1);
                    list.remove(numOfTask - 1);
                    saveTasks(list);
                    System.out.println("Okay bubs I removed: " + taski.toString() + "Now you have " + list.size() + " tasks" +
                            " tasks in the list");

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
