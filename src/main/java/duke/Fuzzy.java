package duke;

import duke.Parser.ParsedInput;
import duke.Parser.Parser;
import duke.Task.*;
import duke.Storage.Storage;
import duke.Ui.Ui;

public class Fuzzy {

    private static final String DATA_FILE_PATH = "./data/fuzzy.txt";

    private static final String MESSAGE_GOODBYE = "See you later! I'll miss you.";
    private static final String MESSAGE_UNKNOWN = "I'm sorry madam, I don't understand that command.";
    private static final String MESSAGE_ERROR = "Baby smth is wrong. Check if you put the details";
    private static final String MESSAGE_MARK_SUCCESS = "Amazing! Well done baby, I've marked it as done:\n";
    private static final String MESSAGE_TASK_ADDED = "Ok madam. I've added this task:\n";

    private final Storage storage;
    private final TaskList list;
    private final Ui ui;

    public Fuzzy() {
        storage = new Storage(DATA_FILE_PATH);
        list = new TaskList(storage.loadTasks());
        ui = new Ui();
    }

    public String getGreeting() {
        return "Hello Madam! I'm Fuzzy.\n At your service😘";
    }

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

    private String handleMark(String details) {
        int taskIndex = Integer.parseInt(details) - 1;
        Task taskToMark = list.get(taskIndex);

        taskToMark.complete();
        storage.saveTasks(list);

        return MESSAGE_MARK_SUCCESS + taskToMark;
    }

    private String handleTodo(String details) {
        if (details == null || details.isBlank()) {
            return "Babe the todo description can't be empty.";
        }

        ToDo newTodo = new ToDo(details);
        list.add(newTodo);
        storage.saveTasks(list);

        return MESSAGE_TASK_ADDED + newTodo
                + "\nNow you have " + list.size() + " tasks in the list.";
    }

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
                    + "\nNow you have " + list.size() + " tasks in the list.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String handleRemove(String details) {
        if (details == null || details.isBlank()) {
            return "Babe tell me which task number to remove.";
        }

        int taskIndex = Integer.parseInt(details) - 1;

        Task removedTask = list.remove(taskIndex);
        storage.saveTasks(list);

        return "Ok madam. I've removed this task:\n"
                + removedTask
                + "\nNow you have " + list.size() + " tasks in the list.";
    }


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

            boolean added;
            if (list.hasEventClash(newEvent)) {
                return "Babe this event clashes with another event.";
            } else {
                list.add(newEvent);
                added = true;
            }

            storage.saveTasks(list);

            return MESSAGE_TASK_ADDED + newEvent
                    + "\nNow you have " + list.size() + " tasks in the list.";

        } catch (Exception e) {
            return "Format: event description /from HHMM /to HHMM";
        }
    }
}
