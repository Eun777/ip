import java.io.IOException;
public class Command {

    public static void execute(String input, TaskList tasks, Ui ui, Storage storage) {
        CommandParser.Command commandType = CommandParser.parseCommand(input);

        switch (commandType) {
        case TODO:
            handleTodo(input, tasks, ui);
            break;
        case DEADLINE:
            handleDeadline(input, tasks, ui);
            break;
        case EVENT:
            handleEvent(input, tasks, ui);
            break;
        case LIST:
            handleList(tasks, ui);
            break;
        case MARK:
            handleMark(input, tasks, ui);
            break;
        case UNMARK:
            handleUnmark(input, tasks, ui);
            break;
        case DELETE:
            handleDelete(input, tasks, ui);
            break;
        case BYE:
            ui.showGoodbyeMessage();
            return;
        default:
            System.out.println("    ____________________________________________________________");
            System.out.println("    Hmm I don't understand what this means.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleTodo(String input, TaskList tasks, Ui ui) {
        try {
            if (input.length() <= 5) {
                throw new EmptyTaskException("OOPS!!! The description of a todo cannot be empty.");
            }
            String description = input.substring(5).trim();
            tasks.addTask(new ToDo(description, false));
            ui.showTaskAdded(tasks.getLastTask(), tasks.getSize());
        } catch (EmptyTaskException e) {
            ui.showErrorMessage(e.getMessage());
        }
    }

    private static void handleDeadline(String input, TaskList tasks, Ui ui) {
        try {
            String[] parts = input.substring(9).trim().split(" /by ");
            if (parts.length < 2) {
                throw new EmptyTaskException("OOPS!!! The description of a deadline cannot be empty.");
            }
            tasks.addTask(new Deadline(parts[0], false, parts[1]));
            ui.showTaskAdded(tasks.getLastTask(), tasks.getSize());
        } catch (EmptyTaskException e) {
            ui.showErrorMessage(e.getMessage());
        }
    }

    private static void handleEvent(String input, TaskList tasks, Ui ui) {
        try {
            String[] parts = input.substring(6).trim().split(" /from | /to ");
            if (parts.length < 3) {
                throw new EmptyTaskException("OOPS!!! The description of an event cannot be empty.");
            }
            tasks.addTask(new Event(parts[0], false, parts[1], parts[2]));
            ui.showTaskAdded(tasks.getLastTask(), tasks.getSize());
        } catch (EmptyTaskException e) {
            ui.showErrorMessage(e.getMessage());
        }
    }

    private static void handleList(TaskList tasks, Ui ui) {
        ui.showTaskList(tasks);
    }

    private static void handleMark(String input, TaskList tasks, Ui ui) {
        try {
            int index = Integer.parseInt(input.substring(5).trim()) - 1;
            tasks.getTask(index).markTask();
            ui.showTaskMarked(tasks.getTask(index), tasks.getSize());
        } catch (Exception e) {
            ui.showErrorMessage("Please enter a valid task number after 'mark'.");
        }
    }

    private static void handleUnmark(String input, TaskList tasks, Ui ui) {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.getTask(index).unmarkTask();
            ui.showTaskUnmarked(tasks.getTask(index), tasks.getSize());
        } catch (Exception e) {
            ui.showErrorMessage("Please enter a valid task number after 'unmark'.");
        }
    }

    private static void handleDelete(String input, TaskList tasks, Ui ui) {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            Task deletedTask = tasks.getTask(index);
            tasks.deleteTask(index);
            ui.showTaskDeleted(deletedTask, tasks.getSize());
        } catch (Exception e) {
            ui.showErrorMessage("Please enter a valid task number to delete.");
        }
    }
}
