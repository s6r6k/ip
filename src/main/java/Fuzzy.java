import java.util.Scanner;
import java.util.ArrayList;

public class Fuzzy {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
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
                    System.out.println(list.get(i));
                }
            } else {
                totalTasks++;
                list.add(String.format("%d: %s", totalTasks, input));
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
            input = scanner.nextLine();
        }
        System.out.println("Alright, see you again.");
        scanner.close();
        System.out.println(line);
    }
}
