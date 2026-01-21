import java.util.Scanner;

public class Fuzzy {
    public static void main(String[] args) {
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
            System.out.println(input);
            System.out.println(line);
            input = scanner.nextLine();
        }
        System.out.println("Alright, see you again.");
        scanner.close();
        System.out.println(line);
    }
}
