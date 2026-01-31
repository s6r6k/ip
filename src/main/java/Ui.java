import java.util.Scanner;

 public class Ui {
     private Scanner scanner = new Scanner(System.in);

     public void showLine() {
         System.out.println("___________________________________");
     }

     public void showWelcome() {
         showLine();
         System.out.println("Hello I'm Fuzzy!");
         System.out.println("What can I do for you babe?");
         showLine();
     }

     public String readCommand() {
         return scanner.nextLine();
     }

     public void showMessage(String msg) {
         System.out.println(msg);
     }

     public void showError(String msg) {
         System.out.println(msg);
     }
 }
