import java.util.Scanner;
import java.util.ArrayList;

public class Fuzzy {

    public static void main(String[] args) {
        class Task {
            private boolean completed;
            private String name;

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
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    Task curr = list.get(i);
                    System.out.println((i+1) + "." + curr.toString());
                }
            }
            else if (input.split(" ", 2)[0].equalsIgnoreCase("mark")){
                int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                Task curry = list.get(taskNum - 1);
                curry.complete();
                System.out.println("I marked it, madam:" + "\n" + curry.toString());
            }
            else if (input.split(" ", 2)[0].equalsIgnoreCase("unmark")) {
                int taskNum = Integer.parseInt(input.split(" ", 2)[1]);
                Task curry = list.get(taskNum - 1);
                curry.unmark();
                System.out.println("Ok, unmarked it bubs:" + "\n" + curry.toString());
            }
            else {
                totalTasks++;
                Task inputO = new Task(input);
                list.add(inputO);
                System.out.println(line);
                System.out.println("added: " + inputO.getName());
                System.out.println(line);
            }
            input = scanner.nextLine();
        }
        System.out.println("Alright, see you again.");
        scanner.close();
        System.out.println(line);
    }
}
