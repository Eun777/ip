package eunAI.ui;

import java.util.Scanner;

import eunAI.TaskList;
import eunAI.task.Task;

// JavaDocs for eunAI.ui package generated by ChatGPT

/**
 * Handles all interactions with the user.
 * Displays messages to the user and reads user input.
 */
public class Ui {

    /** Scanner object to read user input from the console. */
    private Scanner scanner;

    /**
     * Constructs a {@code Ui} object and initializes the input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcomeMessage() {
        showLine();
        System.out.println("    Hello! I'm eunAI");
        System.out.println("    What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbyeMessage() {
        showLine();
        System.out.println("    Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Reads the next command input from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showErrorMessage(String errorMessage) {
        showLine();
        System.out.println("    oopsies! " + errorMessage);
        showLine();
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after the addition.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("    Added the task! Remember to do it!");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after the deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        showLine();
        System.out.println("    Oki! I've deleted this task since you said so...");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @param totalTasks The total number of remaining tasks.
     */
    public void showTaskMarked(Task task, int totalTasks) {
        showLine();
        System.out.println("    You've completed a task? Good job! I have marked it as done.");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events left. You got this!");
        showLine();
    }

    /**
     * Displays a message confirming that a task has been unmarked.
     *
     * @param task The task that was unmarked.
     * @param totalTasks The total number of tasks after unmarking.
     */
    public void showTaskUnmarked(Task task, int totalTasks) {
        showLine();
        System.out.println("    You didn't complete the task? Please do it and stop procrastinating please...");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You now have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

    /**
     * Displays the list of all tasks.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public void showTaskList(TaskList taskList) {
        showLine();
        if (taskList.getSize() == 0) {
            System.out.println("    You have no tasks left. GOOD JOB!!!");
        } else {
            System.out.println("    You still have some uncompleted tasks or future events:");
            for (int i = 0; i < taskList.getSize(); i++) {
                System.out.println("    " + (i + 1) + ". " + taskList.getTask(i).getTaskString());
            }
        }
        showLine();
    }

    /**
     * Displays tasks that match the user's search keyword.
     *
     * @param taskList The list of tasks that match the search criteria.
     */
    public void showFoundTasks(TaskList taskList) {
        showLine();
        if (taskList.getSize() == 0) {
            System.out.println("    No tasks with this keyword found...");
        } else {
            System.out.println("    Tasks found:");
            for (int i = 0; i < taskList.getSize(); i++) {
                System.out.println("    " + (i + 1) + ". " + taskList.getTask(i).getTaskString());
            }
        }
        showLine();
    }

    /**
     * Displays a separator line to improve the readability of console outputs.
     */
    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }
}
