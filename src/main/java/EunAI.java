import java.util.ArrayList;
import java.util.Scanner;
public class EunAI {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello! I'm EunAI");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();
            CommandParser.Command command = CommandParser.parseCommand(input);
            switch (command) {
                case TODO:
                    handleTodo(input, taskList);
                    break;
                case DEADLINE:
                    handleDeadline(input, taskList);
                    break;
                case EVENT:
                    handleEvent(input, taskList);
                    break;
                case LIST:
                    handleList(input, taskList);
                    break;
                case MARK:
                    handleMark(input, taskList);
                    break;
                case UNMARK:
                    handleUnmark(input, taskList);
                    break;
                case DELETE:
                    handleDelete(input, taskList);
                    break;
                case BYE:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Bye. Hope to see you again soon!");
                    System.out.println("    ____________________________________________________________");
                    scanner.close();
                    return;
                default:
                    System.out.println("    ____________________________________________________________");
                    System.out.println("    Hmm I don't understand what this means.");
                    System.out.println("    ____________________________________________________________");
            }
        }
    }

    private static void handleTodo(String input, ArrayList<Task> taskList) {
        try {
            if (input.length() <= 5) { // "todo " is 5 characters
                throw new EmptyTaskException("OOPS!!! The description of a todo cannot be empty.");
            }
            String description = input.substring(5).trim(); // Remove "todo"
            taskList.add(new ToDo(description));
            System.out.println("    ____________________________________________________________");
            System.out.println("    Added the task! Remember to do it!");
            System.out.println("    " + taskList.getLast().getTaskString());
            System.out.println("    You have " + (taskList.size()) + " scheduled tasks and events.");
            System.out.println("    ____________________________________________________________");
        } catch (EmptyTaskException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    OOPS!!! The description of a todo cannot be empty.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleDeadline(String input, ArrayList<Task> taskList) {
        try {
            if (input.length() <= 9) { // "deadline " is 9 characters
                throw new EmptyTaskException("OOPS!!! The description of a deadline cannot be empty.");
            }
            String details = input.substring(9).trim();
            String[] parts = details.split(" /by ");
            taskList.add(new Deadline(parts[0], parts[1]));
            System.out.println("    ____________________________________________________________");
            System.out.println("    Added the task! Stick to the deadline please :)");
            System.out.println("    " + taskList.getLast().getTaskString());
            System.out.println("    You have " + (taskList.size()) + " scheduled tasks and events.");
            System.out.println("    ____________________________________________________________");
        } catch (EmptyTaskException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    OOPS!!! The description of a deadline cannot be empty.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleEvent(String input, ArrayList<Task> taskList) {
        try {
            if (input.length() <= 6) {
                throw new EmptyTaskException("OOPS!!! The description of an event cannot be empty.");
            }
            String details = input.substring(6).trim();
            String[] parts = details.split(" /from | /to ");
            String description = parts[0];
            String from = parts[1];
            String to = parts[2];
            taskList.add(new Event(description, from, to));
            System.out.println("    ____________________________________________________________");
            System.out.println("    I have scheduled the event for you!");
            System.out.println("    " + taskList.getLast().getTaskString());
            System.out.println("    You have " + taskList.size() + " scheduled tasks and events.");
            System.out.println("    ____________________________________________________________");
        } catch (EmptyTaskException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    OOPS!!! The description of an event cannot be empty.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleList(String input, ArrayList<Task> taskList) {
        System.out.println("    ____________________________________________________________");
        for (int i = 0; i <= taskList.size() - 1; i++) {
            System.out.print("      ");
            System.out.print(i + 1);
            System.out.print(".");
            (taskList.get(i)).printTask();
        }
        System.out.println("    You have " + taskList.size() + " scheduled tasks and events.");
        System.out.println("    ____________________________________________________________");
    }

    public static void handleMark(String input, ArrayList<Task> taskList) {
        try {
            int toMark = Integer.parseInt(input.substring(5).trim()) - 1;

            if (toMark >= 0 && toMark < taskList.size()) {
                taskList.get(toMark).markTask();
                System.out.println("    ____________________________________________________________");
                System.out.println("    You've completed a task? Good job! I have marked it as done:");
                System.out.print("      ");
                taskList.get(toMark).printTask();
                System.out.println("    ____________________________________________________________");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    Oops! That task number doesn't exist.");
                System.out.println("    ____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    Please enter a valid number after 'mark'.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleUnmark(String input, ArrayList<Task> taskList) {
        try {
            int toMark = Integer.parseInt(input.substring(7).trim()) - 1;

            if (toMark >= 0 && toMark < taskList.size()) {
                taskList.get(toMark).unmarkTask();
                System.out.println("    ____________________________________________________________");
                System.out.println("    You didn't completed the task? Please do it:");
                System.out.print("      ");
                taskList.get(toMark).printTask();
                System.out.println("    ____________________________________________________________");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    Oops! That task number doesn't exist.");
                System.out.println("    ____________________________________________________________");
            }
        } catch (NumberFormatException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    Please enter a valid number after 'mark'.");
            System.out.println("    ____________________________________________________________");
        }
    }

    private static void handleDelete(String input, ArrayList<Task> taskList) {
        try {
            if (input.length() <= 7) {
                throw new EmptyTaskException("OOPS!!! You forgot which task to delete?");
            }
            int toDelete = Integer.parseInt(input.substring(7).trim());
            if (toDelete > taskList.size()) {
                throw new TaskOutOfBoundsException("OOPS!!! This task does not even exist. Try again.");
            }
            System.out.println("    ____________________________________________________________");
            System.out.println("    Oki! I've deleted this task since you said so...");
            System.out.println("    " + taskList.get(toDelete - 1).getTaskString());
            System.out.println("    There's only " + (taskList.size() - 1) + " tasks left in the list.");
            System.out.println("    ____________________________________________________________");
            taskList.remove(toDelete - 1);

        } catch (EmptyTaskException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    OOPS!!! You forgot which task to delete?");
            System.out.println("    ____________________________________________________________");
        } catch (TaskOutOfBoundsException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    OOPS!!! This task does not even exist. Try again.");
            System.out.println("    ____________________________________________________________");
        }
    }
}



