package duke.exception;

public class EmptyDescException extends Exception {
    public EmptyDescException(String command) {
        super("Ermmm the description of " + command + " can't be blank la.");
    }
}
