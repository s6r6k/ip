package duke;

import duke.Parser.ParsedInput;
import duke.Parser.Parser;
import duke.Task.Task;
import duke.Task.TaskList;
import duke.Task.ToDo;
import duke.Task.Deadline;
import duke.Task.Event;
import duke.Storage.Storage;
import duke.Ui.Ui;

/**
 * The main logic class for the Fuzzy chatbot.
 * It processes user input, delegates commands, and coordinates between
 * the UI, storage, and task list.
 */
public class Fuzzy {

    private static final String DATA_FILE_PATH = "./data/fuzzy.txt";

    private static final String MESSAGE_GOODBYE = "See you later! I'll miss you.";
    private static final String MESSAGE_UNKNOWN = "I'm sorry madam, I don't understand that command.";
    private static final String MESSAGE_ERROR = "Baby smth is wrong. Check if you put the details";
    private static final String MESSAGE_MARK_SUCCESS = "Amazing! Well done baby, I've marked it as done:\n";
    private static final String MESSAGE_TASK_ADDED = "Ok madam. I've added this task:\n";
    private static final String MESSAGE_UNMARK_SUCCESS = "Alright bubs, I've marked this task as not done yet:\n";

    private final Storage storage;
    private final TaskList list;
    private final Ui ui;

    /**
     * Constructs a Fuzzy chatbot instance.
     * Initializes storage, loads saved tasks, and prepares the UI.
     */
    public Fuzzy() {
        storage = new Storage(DATA_FILE_PATH);
        list = new TaskList(storage.loadTasks());
        ui = new Ui();
    }

    /**
     * Returns the greeting message shown when the chatbot starts.
     *
     * @return Greeting message for the user.
     */
    public String getGreeting() {
        return "Hello Madam! I'm Fuzzy.\n At your service😘";
    }

    /**
     * Processes user input and returns the chatbot's response.
     * Handles parsing errors and runtime exceptions gracefully.
     *
     * @param input User input string.
     * @return Response message to be displayed to the user.
     */
    public String getResponse(String input) {
        try {
            ParsedInput parsedInput = Parser.parse(input);
            if (parsedInput == null) {
                return MESSAGE_UNKNOWN;
            }

            return handleCommand(parsedInput);

        } catch (NumberFormatException e) {
            return "Bubs needa give a valid number/time.";
        } catch (IndexOutOfBoundsException e) {
            return "Babe this task no. does not exist.";
        } catch (Exception e) {
            return MESSAGE_ERROR;
        }
    }

    /**
     * Executes the command based on the parsed input.
     *
     * @param parsedInput Parsed command and details.
     * @return Result message after executing the command.
     */
    private String handleCommand(ParsedInput parsedInput) {
        Parser.CommandType type = parsedInput.getCommandType();
        String details = parsedInput.getDetails();

        switch (type) {
        case BYE:
            return MESSAGE_GOODBYE;

        case LIST:
            return list.getFormattedTasks();

        case REMOVE:
            return handleRemove(details);

        case MARK:
            return handleMark(details);

        case UNMARK:
            return handleUnmark(details);

        case TODO:
            return handleTodo(details);

        case DEADLINE:
            return handleDeadline(details);

        case EVENT:
            return handleEvent(details);

        case UNKNOWN:
            return MESSAGE_UNKNOWN;

        default:
            return MESSAGE_ERROR;
        }
    }

    /**
     * Marks a task as completed.
     *
     * @param details Task index provided by the user.
     * @return Confirmation message.
     */
    private String handleMark(String details) {
        int taskIndex = Integer.parseInt(details) - 1;
        Task taskToMark = list.get(taskIndex);

        taskToMark.complete();
        storage.saveTasks(list);

        return MESSAGE_MARK_SUCCESS + taskToMark;
    }

    /**
     * Marks a task as not completed.
     *
     * @param details Task index provided by the user.
     * @return Confirmation message.
     */
    private String handleUnmark(String details) {
        int taskIndex = Integer.parseInt(details) - 1;
        Task taskToUnmark = list.get(taskIndex);

        taskToUnmark.unmark();
        storage.saveTasks(list);

        return MESSAGE_UNMARK_SUCCESS + taskToUnmark;
    }


    /**
     * Adds a new todo task.
     *
     * @param details Description of the todo task.
     * @return Confirmation message or error message.
     */
    private String handleTodo(String details) {
        if (details == null || details.isBlank()) {
            return "Babe the todo description can't be empty.";
        }

        ToDo newTodo = new ToDo(details);
        list.add(newTodo);
        storage.saveTasks(list);

        return MESSAGE_TASK_ADDED + newTodo
                + "\nNow you have " + list.getSize() + " tasks in the list.";
    }

    /**
     * Adds a new deadline task.
     *
     * @param details Deadline description and date.
     * @return Confirmation message or format error.
     */
    private String handleDeadline(String details) {
        if (details == null || details.isBlank()) {
            return "Format: deadline description /by yyyy-mm-dd";
        }

        try {
            String[] parts = details.split(" /by ");

            if (parts.length < 2) {
                return "Format: deadline description /by yyyy-mm-dd";
            }

            String description = parts[0].trim();
            String date = parts[1].trim();

            Deadline newDeadline = new Deadline(date, description);
            list.add(newDeadline);
            storage.saveTasks(list);

            return MESSAGE_TASK_ADDED + newDeadline
                    + "\nNow you have " + list.getSize() + " tasks in the list.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Removes a task from the list.
     *
     * @param details Task index provided by the user.
     * @return Confirmation message.
     */
    private String handleRemove(String details) {
        if (details == null || details.isBlank()) {
            return "Babe tell me which task number to remove.";
        }

        int taskIndex = Integer.parseInt(details) - 1;

        Task removedTask = list.remove(taskIndex);
        storage.saveTasks(list);

        return "Ok madam. I've removed this task:\n"
                + removedTask
                + "\nNow you have " + list.getSize() + " tasks in the list.";
    }

    /**
     * Adds a new event task after checking for time clashes.
     *
     * @param details Event description and time range.
     * @return Confirmation message or error message.
     */
    private String handleEvent(String details) {
        if (details == null || details.isBlank()) {
            return "Format: event description /from HHMM /to HHMM";
        }

        try {
            String[] parts = details.split(" /from | /to ");

            if (parts.length < 3) {
                return "Format: event description /from HHMM /to HHMM";
            }

            String description = parts[0].trim();
            String start = parts[1].trim();
            String end = parts[2].trim();

            Event newEvent = new Event(start, end, description);

            if (list.hasEventClash(newEvent)) {
                return "Babe this event clashes with another event.";
            } else {
                list.add(newEvent);
            }

            storage.saveTasks(list);

            return MESSAGE_TASK_ADDED + newEvent
                    + "\nNow you have " + list.getSize() + " tasks in the list.";

        } catch (Exception e) {
            return "Format: event description /from HHMM /to HHMM";
        }
    }
}
