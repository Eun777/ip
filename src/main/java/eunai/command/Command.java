package eunai.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eunai.Storage;
import eunai.TaskList;
import eunai.exception.EmptyTaskException;
import eunai.task.Deadline;
import eunai.task.Event;
import eunai.task.Task;
import eunai.task.ToDo;
import eunai.ui.Ui;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * Represents a handler for all user commands.
 * It parses the command input and dispatches it to specific handlers
 * (e.g., add, delete, mark, unmark).
 */
public class Command {

    /**
     * Executes the user command string by determining whether it is a mass operation
     * (comma-separated indices) or a single command, then routes to the appropriate method.
     *
     * @param input   The full user input command
     * @param tasks   The TaskList managing the user's tasks
     * @param ui      The Ui object for user interaction
     * @param storage The Storage object for saving/loading tasks
     * @return A response message indicating the result of the command
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
        return routeCommand(commandType, input, tasks, ui, storage);
    }

    /**
     * Routes the parsed command type to the corresponding handler method.
     *
     * @param commandType The parsed command type from the user input
     * @param input       The raw user input string
     * @param tasks       The TaskList containing tasks
     * @param ui          The Ui for user interaction
     * @param storage     The Storage for saving/loading tasks
     * @return A result message describing the outcome of the operation
     */
    private static String routeCommand(CommandParser.Command commandType, String input,
                                       TaskList tasks, Ui ui, Storage storage) {
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
            return "Hmm, I don't understand what this means.\n"
                    + "Try valid commands like: todo, deadline, event, list, mark, unmark, find, delete, bye.";
        }
    }

    /**
     * Handles mass operations such as "delete 2,5,6" or "mark 1,2,4" by parsing all indices,
     * sorting them in descending order, and applying the requested operation.
     *
     * @param input The user command string containing the operation and comma-separated indices
     * @param tasks The TaskList that stores the user's tasks
     * @return A result message detailing processed tasks and skipped invalid indices
     */
    public static String handleMassOps(String input, TaskList tasks) {
        CommandParser.Command commandType = CommandParser.parseCommand(input);
        int prefixLength = getCommandPrefixLength(commandType);
        String indicesPart = input.substring(prefixLength).trim();
        String[] rawIndices = indicesPart.split("\\s*,\\s*");

        List<Integer> validIndices = new ArrayList<>();
        List<String> invalidIndices = new ArrayList<>();
        for (String indexStr : rawIndices) {
            try {
                int userIndex = Integer.parseInt(indexStr);
                if (userIndex <= 0) {
                    invalidIndices.add(indexStr + " (must be > 0)");
                } else if (userIndex > tasks.getSize()) {
                    invalidIndices.add(indexStr + " (out of range)");
                } else {
                    validIndices.add(userIndex - 1);
                }
            } catch (NumberFormatException e) {
                invalidIndices.add(indexStr + " (not a valid number)");
            }
        }
        validIndices.sort(Collections.reverseOrder());
        StringBuilder result = new StringBuilder();
        for (int idx : validIndices) {
            Task task = tasks.getTask(idx);
            switch (commandType) {
            case MARK:
                task.markTask();
                result.append("Marked task ").append(idx + 1).append(" as done:\n")
                        .append(task.getTaskString()).append("\n");
                break;
            case UNMARK:
                task.unmarkTask();
                result.append("Unmarked task ").append(idx + 1).append(":\n")
                        .append(task.getTaskString()).append("\n");
                break;
            case DELETE:
                tasks.deleteTask(idx);
                result.append("Deleted task ").append(idx + 1).append(":\n")
                        .append(task.getTaskString()).append("\n");
                break;
            default:
                break;
            }
        }
        if (!invalidIndices.isEmpty()) {
            result.append("\nSkipped invalid task numbers:\n");
            for (String bad : invalidIndices) {
                result.append("  - ").append(bad).append("\n");
            }
        }
        if (commandType == CommandParser.Command.DELETE) {
            result.append("\nNow you have ").append(tasks.getSize()).append(" tasks in the list.");
        }

        return result.toString().trim();
    }

    /**
     * Gets the length of the command prefix for mass operations
     * (e.g., "mark ", "unmark ", "delete ").
     *
     * @param commandType The type of mass operation command
     * @return The integer length to substring away from the user input
     * @throws IllegalArgumentException If the command is not a valid mass operation
     */
    private static int getCommandPrefixLength(CommandParser.Command commandType) {
        switch (commandType) {
        case MARK:
            return 5;
        case UNMARK, DELETE:
            return 7;
        default:
            throw new IllegalArgumentException("Invalid mass operation command.");
        }
    }

    /**
     * Handles creation of a new ToDo task from user input (e.g., "todo read a book").
     *
     * @param input The full user command
     * @param tasks The TaskList where the new task is added
     * @return A message showing the added task or an error message if invalid
     */
    private static String handleTodo(String input, TaskList tasks) {
        try {
            String description = extractTodoDescription(input);
            Task newTask = new ToDo(description, false);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage() + "\nUsage: todo <description>";
        }
    }

    /**
     * Extracts the ToDo description from a user input (e.g., "todo read a book").
     *
     * @param input The full user command
     * @return The parsed description
     * @throws EmptyTaskException If the user did not provide any description
     */
    private static String extractTodoDescription(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("OOPS!!! The description of a todo cannot be empty.");
        }
        return input.substring(5).trim();
    }

    /**
     * Handles creation of a new Deadline task from user input
     * (e.g., "deadline finish project /by 2025-02-28").
     *
     * @param input The full user command containing description and /by part
     * @param tasks The TaskList where the new task is added
     * @return A message showing the added deadline or an error if invalid
     */
    private static String handleDeadline(String input, TaskList tasks) {
        try {
            String[] parts = extractDeadlineDetails(input);
            Task newTask = new Deadline(parts[0], false, parts[1]);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage()
                    + "\nUsage: deadline <description> /by <date/time>\n"
                    + "e.g. deadline Finish project /by 2025-02-28 23:59";
        }
    }

    /**
     * Extracts the description and date/time for a deadline command.
     *
     * @param input The user command (e.g., "deadline finish /by tomorrow")
     * @return A string array with two elements: [description, byDateTime]
     * @throws EmptyTaskException If either the description or date/time is missing
     */
    private static String[] extractDeadlineDetails(String input) throws EmptyTaskException {
        String[] parts = input.substring(9).trim().split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new EmptyTaskException(
                    "OOPS!!! A deadline requires a description and a '/by' date/time."
            );
        }
        return parts;
    }

    /**
     * Handles creation of a new Event task from user input
     * (e.g., "event meeting /from 2pm /to 4pm").
     *
     * @param input The full user command containing description, /from, and /to parts
     * @param tasks The TaskList where the new event is added
     * @return A message showing the added event or an error if invalid
     */
    private static String handleEvent(String input, TaskList tasks) {
        try {
            String[] parts = input.substring(6).trim().split(" /from | /to ");
            if (parts.length < 3) {
                throw new EmptyTaskException(
                        "OOPS!!! An event requires: <description> /from <start> /to <end>."
                );
            }
            Task newTask = new Event(parts[0], false, parts[1], parts[2]);
            tasks.addTask(newTask);
            return getTaskAddedMessage(newTask, tasks);
        } catch (EmptyTaskException e) {
            return e.getMessage()
                    + "\nUsage: event <description> /from <date/time> /to <date/time>\n"
                    + "e.g. event Meeting /from 2025-02-21 10:00 /to 2025-02-21 12:00";
        }
    }

    /**
     * Lists all tasks in the user's task list.
     *
     * @param tasks The TaskList storing the user's tasks
     * @return The formatted list of tasks or a message if empty
     */
    private static String handleList(TaskList tasks) {
        return (tasks.getSize() == 0)
                ? "Your task list is empty. Why not add some tasks?"
                : tasks.getListString();
    }

    /**
     * Marks a specified task (by index) as done.
     *
     * @param input The user command (e.g., "mark 2")
     * @param tasks The TaskList storing tasks
     * @return A success message if valid or an error message if invalid
     */
    private static String handleMark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 5);
            task.markTask();
            return "Great job! I've marked this task as done:\n" + task.getTaskString();
        } catch (NumberFormatException nfe) {
            return "Oh no...Please enter a valid number after 'mark'. e.g. 'mark 2'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Uhhh the task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Welp! Unable to mark. Please enter a valid task number. e.g. 'mark 2'";
        }
    }

    /**
     * Marks a specified task (by index) as not done.
     *
     * @param input The user command (e.g., "unmark 3")
     * @param tasks The TaskList storing tasks
     * @return A success message if valid or an error message if invalid
     */
    private static String handleUnmark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 7);
            task.unmarkTask();
            return "Alright, I've marked this task as NOT done:\n" + task.getTaskString();
        } catch (NumberFormatException nfe) {
            return "Oh no...Please enter a valid number after 'unmark'. e.g. 'unmark 3'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Uhhh the task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Welp! Unable to unmark. Please enter a valid task number. e.g. 'unmark 3'";
        }
    }

    /**
     * Deletes a specified task (by index) from the user's task list.
     *
     * @param input The user command (e.g., "delete 2")
     * @param tasks The TaskList storing tasks
     * @return A success message if valid or an error message if invalid
     */
    private static String handleDelete(String input, TaskList tasks) {
        try {
            int index = getTaskIndex(input, 7);
            Task task = tasks.getTask(index);
            tasks.deleteTask(index);
            return "I've removed this task:\n" + task.getTaskString()
                    + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        } catch (NumberFormatException nfe) {
            return "Oh no...Please enter a valid number after 'delete'. e.g. 'delete 2'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Uhhh that task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Welp! Unable to delete. Please enter a valid task number. e.g. 'delete 2'";
        }
    }

    /**
     * Finds tasks that match a keyword or a special marker.
     *
     * @param input The user command - find
     * @param tasks The TaskList containing tasks
     * @return A list of matching tasks or an error message if the search is invalid
     */
    private static String handleFind(String input, TaskList tasks) {
        try {
            String keyword = extractFindKeyword(input);
            TaskList foundTasks = filterTasksByKeyword(tasks, keyword);
            if (foundTasks.getSize() == 0) {
                return "No tasks match '" + keyword + "'. Try another keyword!";
            }
            return "Found " + foundTasks.getSize() + " matching task(s):\n"
                    + foundTasks.getListString();
        } catch (EmptyTaskException e) {
            return e.getMessage()
                    + "\nUsage: find <keyword>\n"
                    + "Alternatively, find <task-type> using <todo>, <deadline>, or <event>.\n"
                    + "e.g. 'find <deadline>'";
        } catch (Exception e) {
            return "Sorry manz, that was an invalid search."
                    + "Please provide a keyword after 'find'. e.g. 'find homework'";
        }
    }

    /**
     * Extracts the keyword for a find operation from the user command.
     *
     * @param input The user command (e.g., "find meeting")
     * @return The keyword string
     * @throws EmptyTaskException If no keyword is provided after "find "
     */
    private static String extractFindKeyword(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("Oops! The keyword for 'find' cannot be empty.");
        }
        return input.substring(5).trim();
    }

    /**
     * Filters tasks by a task types or descriptions by substring.
     *
     * @param tasks   The TaskList containing all tasks
     * @param keyword The keyword or special type indicator
     * @return A TaskList of matching tasks
     */
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

    /**
     * Handles the exit command by saving tasks to storage and scheduling
     * the application to exit shortly.
     *
     * @param tasks   The TaskList containing tasks
     * @param storage The Storage for saving tasks
     * @return A farewell message indicating whether tasks were saved
     */
    private static String handleExit(TaskList tasks, Storage storage) {
        boolean isSaved = storage.saveTasks(tasks.getAllTasks());
        if (!isSaved) {
            return "Oops! I couldn't save your tasks. Something went wrong. Please try again.";
        }

        // Delay before exiting (useful in JavaFX applications)
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();

        return "Byeee! I've successfully saved your tasks. See you next time!";
    }

    /**
     * Retrieves a task from the TaskList by parsing the index from a user command substring.
     *
     * @param input        The user command (e.g., "mark 2")
     * @param tasks        The TaskList containing tasks
     * @param prefixLength The length of the command prefix to strip away
     * @return The Task at the parsed zero-based index
     * @throws NumberFormatException     If the substring cannot be parsed as an integer
     * @throws IndexOutOfBoundsException If the index is negative or out of range
     */
    private static Task getTaskFromInput(String input, TaskList tasks, int prefixLength) {
        int index = getTaskIndex(input, prefixLength);
        return tasks.getTask(index);
    }

    /**
     * Parses and adjusts the index from user input (1-based) to zero-based.
     *
     * @param input        The user command (e.g., "delete 3")
     * @param prefixLength Number of characters to skip (e.g., length of "delete ")
     * @return The zero-based index
     * @throws NumberFormatException     If the substring is not an integer
     * @throws IndexOutOfBoundsException If the index is negative
     */
    private static int getTaskIndex(String input, int prefixLength) {
        int index = Integer.parseInt(input.substring(prefixLength).trim()) - 1;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Task index cannot be negative.");
        }
        return index;
    }

    /**
     * Constructs a message confirming that a new task has been added and displaying
     * the current number of tasks in the list.
     *
     * @param task  The newly created Task
     * @param tasks The TaskList containing the newly added task
     * @return A success message showing the task and updated list size
     */
    private static String getTaskAddedMessage(Task task, TaskList tasks) {
        return "Done! I've added this task:\n" + task.getTaskString()
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }
}
