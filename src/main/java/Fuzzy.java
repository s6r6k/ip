public class Fuzzy {
    public static void main(String[] args) {
        String line = "";
        for (int i = 0; i < 35; i++) {
            line += "_";
        }
        System.out.println(line);
        String greeting = "Hello I'm Fuzzy!" + "\n" + "What can I do for you babe?";
        System.out.println(greeting + "\n" + line);
        System.out.println("Bye. See you soon.");
        System.out.println(line);
    }
}
