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
 * Handles the execution of user commands.
 * This class processes different types of commands (e.g., add, delete, mark, unmark)
 * and interacts with {@link TaskList}, {@link Ui}, and {@link Storage} to perform the
 * appropriate actions.
 */
public class Command {

    public static String execute(String input, TaskList tasks, Ui ui, Storage storage) {
        assert input != null : "input should not be null";
        assert tasks != null : "tasks should not be null";
        assert ui != null : "ui should not be null";
        assert storage != null : "storage should not be null";

        // Check if it's a mass operation (commas in input)
        if (input.contains(",")) {
            return handleMassOps(input, tasks);
        }

        // Otherwise, single command execution
        CommandParser.Command commandType = CommandParser.parseCommand(input);
        return routeCommand(commandType, input, tasks, ui, storage);
    }

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
     * Improved handleMassOps: parse all indices, sort in descending order, then apply operations.
     */
    public static String handleMassOps(String input, TaskList tasks) {
        CommandParser.Command commandType = CommandParser.parseCommand(input);
        int prefixLength = getCommandPrefixLength(commandType);

        // 1. Extract the part after the command word
        String indicesPart = input.substring(prefixLength).trim();
        String[] rawIndices = indicesPart.split("\\s*,\\s*");

        // 2. Parse and collect valid indices, track invalid
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
                    // Convert user-facing index to zero-based
                    validIndices.add(userIndex - 1);
                }
            } catch (NumberFormatException e) {
                invalidIndices.add(indexStr + " (not a valid number)");
            }
        }

        // 3. Sort valid indices descending
        validIndices.sort(Collections.reverseOrder());

        // 4. Apply operations in descending order
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

        // 5. Summarize invalid indices
        if (!invalidIndices.isEmpty()) {
            result.append("\nSkipped invalid task numbers: \n");
            for (String bad : invalidIndices) {
                result.append("  - ").append(bad).append("\n");
            }
        }

        // 6. Final message if it's a DELETE
        if (commandType == CommandParser.Command.DELETE) {
            result.append("\nNow you have ").append(tasks.getSize()).append(" tasks in the list.");
        }

        return result.toString().trim();
    }

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

    /* ======================= Single-Command Handlers Below ======================= */

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

    private static String extractTodoDescription(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("OOPS!!! The description of a todo cannot be empty.");
        }
        return input.substring(5).trim();
    }

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

    private static String[] extractDeadlineDetails(String input) throws EmptyTaskException {
        String[] parts = input.substring(9).trim().split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new EmptyTaskException(
                    "OOPS!!! A deadline requires a description and a '/by' date/time."
            );
        }
        return parts;
    }

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

    private static String handleList(TaskList tasks) {
        return (tasks.getSize() == 0)
                ? "Your task list is empty. Why not add some tasks?"
                : tasks.getListString();
    }

    private static String handleMark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 5);
            task.markTask();
            return "Great job! I've marked this task as done:\n" + task.getTaskString();
        } catch (NumberFormatException nfe) {
            return "Error: Please enter a valid number after 'mark'. e.g. 'mark 2'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Error: The task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Error: Unable to mark. Please enter a valid task number. e.g. 'mark 2'";
        }
    }

    private static String handleUnmark(String input, TaskList tasks) {
        try {
            Task task = getTaskFromInput(input, tasks, 7);
            task.unmarkTask();
            return "Alright, I've marked this task as NOT done:\n" + task.getTaskString();
        } catch (NumberFormatException nfe) {
            return "Error: Please enter a valid number after 'unmark'. e.g. 'unmark 3'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Error: The task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Error: Unable to unmark. Please enter a valid task number. e.g. 'unmark 3'";
        }
    }

    private static String handleDelete(String input, TaskList tasks) {
        try {
            int index = getTaskIndex(input, 7);
            Task task = tasks.getTask(index);
            tasks.deleteTask(index);
            return "I've removed this task:\n" + task.getTaskString()
                    + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        } catch (NumberFormatException nfe) {
            return "Error: Please enter a valid number after 'delete'. e.g. 'delete 2'";
        } catch (IndexOutOfBoundsException ioobe) {
            return "Error: That task number is out of range. Check your list with 'list'.";
        } catch (Exception e) {
            return "Error: Unable to delete. Please enter a valid task number. e.g. 'delete 2'";
        }
    }

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
            return "Error: Invalid search. Please provide a keyword after 'find'. e.g. 'find homework'";
        }
    }

    private static String extractFindKeyword(String input) throws EmptyTaskException {
        if (input.length() <= 5) {
            throw new EmptyTaskException("Oops! The keyword for 'find' cannot be empty.");
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
        boolean isSaved = storage.saveTasks(tasks.getAllTasks());
        if (!isSaved) {
            return "Oops! I couldn't save your tasks. Something went wrong. Please try again.";
        }

        // Delay before exiting (for JavaFX usage)
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();

        return "Byeee! I've successfully saved your tasks. See you next time!";
    }

    private static Task getTaskFromInput(String input, TaskList tasks, int prefixLength) {
        int index = getTaskIndex(input, prefixLength);
        return tasks.getTask(index);
    }

    private static int getTaskIndex(String input, int prefixLength) {
        int index = Integer.parseInt(input.substring(prefixLength).trim()) - 1;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Task index cannot be negative.");
        }
        return index;
    }

    private static String getTaskAddedMessage(Task task, TaskList tasks) {
        return "Done! I've added this task:\n" + task.getTaskString()
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }
}
