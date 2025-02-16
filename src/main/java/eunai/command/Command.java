package eunai.command;

import java.io.IOException;

import eunai.Storage;
import eunai.TaskList;
import eunai.exception.EmptyTaskException;
import eunai.task.Deadline;
import eunai.task.Event;
import eunai.task.Task;
import eunai.task.ToDo;
import eunai.ui.Ui;


// JavaDocs for eunai.command package generated by ChatGPT

/**
 * Handles the execution of user commands.
 * This class processes different types of commands (e.g., add, delete, mark, unmark)
 * and interacts with {@link TaskList}, {@link Ui}, and {@link Storage} to perform the appropriate actions.
 */
public class Command {

    /**
     * Executes the user command based on the parsed command type.
     * This method routes the input to the correct handler method depending on the command.
     *
     * @param input The user input command as a string.
     * @param tasks The {@link TaskList} that stores the current tasks.
     * @param ui The {@link Ui} object to interact with the user.
     * @param storage The {@link Storage} object to handle task persistence.
     */
    public static String execute(String input, TaskList tasks, Ui ui, Storage storage) {
        assert input != null : "input should not be null";
        assert tasks != null : "tasks should not be null";
        assert ui != null : "ui should not be null";
        assert storage != null : "storage should not be null";

        if (input.contains(",")) {
            return handleMassOps(input, tasks);
        }

        CommandParser.Command commandType = CommandParser.parseCommand(input);
        switch (commandType) {
        case TODO:
            return handleTodo(input, tasks);
        case DEADLINE:
            return handleDeadline(input, tasks);
        case EVENT:
            return handleEvent(input, tasks);
        case LIST:
            return handleList(tasks);
        case MARK:
            return handleMark(input, tasks);
        case UNMARK:
            return handleUnmark(input, tasks);
        case FIND:
            return handleFind(input, tasks);
        case DELETE:
            return handleDelete(input, tasks);
        case BYE:
            return handleExit(tasks, storage);
        default:
            return "Hmm, I don't understand what this means.";
        }
    }

    /**
     * Handles mass operations for marking, unmarking, and deleting multiple tasks at once.
     * <p>
     * This method processes commands where multiple task indices are provided, separated by commas.
     *
     * @param input The user's command string (e.g., {@code "delete 1, 2, 3"}).
     * @param tasks The {@link TaskList} that stores the current tasks.
     * @return A formatted string describing the outcome of the operation.
     */
    public static String handleMassOps(String input, TaskList tasks) {
        CommandParser.Command commandType = CommandParser.parseCommand(input);
        String[] taskIndices = extractTaskIndices(input, commandType);
        StringBuilder result = new StringBuilder();

        for (String indexStr : taskIndices) {
            processMassOperation(commandType, indexStr.trim(), tasks, result);
        }

        return finalizeMassOpsResult(commandType, tasks, result);
    }

    private static String[] extractTaskIndices(String input, CommandParser.Command commandType) {
        int prefixLength = getCommandPrefixLength(commandType);
        return input.substring(prefixLength).trim().split("\\s*,\\s*");
    }

    private static int getCommandPrefixLength(CommandParser.Command commandType) {
        switch (commandType) {
        case MARK:
            return 5;
        case UNMARK:
            return 7;
        case DELETE:
            return 7;
        default: throw new IllegalArgumentException("Invalid mass operation command.");
        }
    }

    private static void processMassOperation(CommandParser.Command commandType, String indexStr,
                                             TaskList tasks, StringBuilder result) {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            Task task = tasks.getTask(index);

            switch (commandType) {
            case MARK:
                task.markTask();
                result.append("Marked task as done:\n").append(task.getTaskString()).append("\n");
                break;
            case UNMARK:
                task.unmarkTask();
                result.append("Unmarked task:\n").append(task.getTaskString()).append("\n");
                break;
            case DELETE:
                tasks.deleteTask(index);
                result.append("Deleted task:\n").append(task.getTaskString()).append("\n");
                break;
            default:
                break;
            }
        } catch (Exception e) {
            result.append("Skipped invalid task number: ").append(indexStr).append("\n");
        }
    }

    private static String finalizeMassOpsResult(CommandParser.Command commandType,
                                                TaskList tasks, StringBuilder result) {
        if (commandType == CommandParser.Command.DELETE) {
            result.append("Now you have ").append(tasks.getSize()).append(" tasks in the list.");
        }
        return result.toString();
    }

    /**
     * Handles the addition of a {@link ToDo} task.
     *
     * @param input The user input command containing the to-do description.
     * @param tasks The task list to add the to-do task to.
     */
    private static String handleTodo(String input, TaskList tasks) {
        try {
            String description = extractTodoDescription(input);
            Task newTask = new ToDo(description, false);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage();
        }
    }

    private static String extractTodoDescription(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("OOPS!!! The description of a todo cannot be empty.");
        }
        return input.substring(5).trim();
    }


    /**
     * Handles the addition of a {@link Deadline} task.
     *
     * @param input The user input command containing the deadline description and date.
     * @param tasks The task list to add the deadline task to.
     */
    private static String handleDeadline(String input, TaskList tasks) {
        try {
            String[] parts = extractDeadlineDetails(input);
            Task newTask = new Deadline(parts[0], false, parts[1]);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage();
        }
    }

    private static String[] extractDeadlineDetails(String input) throws EmptyTaskException {
        String[] parts = input.substring(9).trim().split(" /by ");
        if (parts.length < 2) {
            throw new EmptyTaskException("OOPS!!! The description of a deadline cannot be empty.");
        }
        return parts;
    }


    /**
     * Handles the addition of an {@link Event} task.
     *
     * @param input The user input command containing the event description, start date, and end date.
     * @param tasks The task list to add the event task to.
     */
    private static String handleEvent(String input, TaskList tasks) {
        try {
            String[] parts = input.substring(6).trim().split(" /from | /to ");
            if (parts.length < 3) {
                throw new EmptyTaskException("OOPS!!! The description of an event cannot be empty.");
            }

            Task newTask = new Event(parts[0], false, parts[1], parts[2]);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage();
        }
    }

    /**
     * Displays the current list of tasks.
     *
     * @param tasks The task list to display.
     */
    private static String handleList(TaskList tasks) {
        return (tasks.getSize() == 0) ? "Your task list is empty." : tasks.getListString();
    }

    /**
     * Marks a task as completed.
     *
     * @param input The user input command specifying the task index to mark.
     * @param tasks The task list containing the task to mark.
     */
    private static String handleMark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 5);
            task.markTask();
            return "Nice! I've marked this task as done:\n" + task.getTaskString();
        } catch (Exception e) {
            return "Please enter a valid task number after 'mark'.";
        }
    }

    private static Task getTaskFromInput(String input, TaskList tasks, int prefixLength) {
        int index = getTaskIndex(input, prefixLength);
        return tasks.getTask(index);
    }


    /**
     * Marks a task as not completed.
     *
     * @param input The user input command specifying the task index to unmark.
     * @param tasks The task list containing the task to unmark.
     */
    private static String handleUnmark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 7);
            task.unmarkTask();
            return "OK, I've marked this task as not done yet:\n" + task.getTaskString();
        } catch (Exception e) {
            return "Please enter a valid task number after 'unmark'.";
        }
    }

    /**
     * Deletes a task from the task list.
     *
     * @param input The user input command specifying the task index to delete.
     * @param tasks The task list containing the task to delete.
     */
    private static String handleDelete(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 7);
            tasks.deleteTask(getTaskIndex(input, 7)); // Still need index to delete
            return "Noted. I've removed this task:\n" + task.getTaskString()
                    + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        } catch (Exception e) {
            return "Please enter a valid task number to delete.";
        }
    }

    /**
     * Finds and displays tasks that match the given keyword.
     *
     * @param input The user input command containing the search keyword.
     * @param tasks The task list to search within.
     */
    private static String handleFind(String input, TaskList tasks) {
        try {
            String keyword = extractFindKeyword(input);
            TaskList foundTasks = filterTasksByKeyword(tasks, keyword);

            if (foundTasks.getSize() == 0) {
                return "Hmm, no tasks match that search. Try again!";
            }
            return "Found " + foundTasks.getSize() + " matching task(s):\n" + foundTasks.getListString();
        } catch (Exception e) {
            return "Invalid search. Please provide a keyword or task type after 'find'.";
        }
    }

    private static String extractFindKeyword(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("OOPS!! The keyword for find cannot be empty.");
        }
        return input.substring(5).trim();
    }

    private static TaskList filterTasksByKeyword(TaskList tasks, String keyword) {
        switch (keyword.toLowerCase()) {
        case "<todo>":
            return tasks.filterByType("T");
        case "<deadline>":
            return tasks.filterByType("D");
        case "<event>":
            return tasks.filterByType("E");
        default:
            return tasks.findTask(keyword);
        }
    }



    private static String handleExit(TaskList tasks, Storage storage) {
        try {
            storage.saveTasks(tasks.getAllTasks());
            return "Goodbye! Your tasks have been saved. See you next time!";
        } catch (IOException e) {
            return "Sorry, but your tasks could not be saved. Any changes may be lost.";
        }
    }

    private static int getTaskIndex(String input, int prefixLength) {
        assert input.length() > prefixLength : "input should be long enough to contain a task number";
        int index = Integer.parseInt(input.substring(prefixLength).trim()) - 1;
        assert index >= 0 : "task index should not be negative";
        return index;
    }

    private static String getTaskAddedMessage(Task task, TaskList tasks) {
        return "Got it. I've added this task:\n" + task.getTaskString()
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }
}
