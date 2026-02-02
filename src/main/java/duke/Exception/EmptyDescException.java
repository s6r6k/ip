package duke.Exception;

/**
 * If the command written by user is blank, exception is thrown
 * checked exception must be in method descriptor
 * methods that dont declare need to handle the exception
 * @param command written by user
 */
public class EmptyDescException extends Exception {
    public EmptyDescException(String command) {
        super("Ermmm the description of " + command + " can't be blank la.");
    }
}