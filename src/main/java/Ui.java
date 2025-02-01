import java.util.Scanner;
public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcomeMessage() {
        showLine();
        System.out.println("    Hello! I'm EunAI");
        System.out.println("    What can I do for you?");
        showLine();
    }

    public void showGoodbyeMessage() {
        showLine();
        System.out.println("    Bye. Hope to see you again soon!");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showErrorMessage(String errorMessage) {
        showLine();
        System.out.println("    oopsies! " + errorMessage);
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println("    Added the task! Remember to do it!");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        showLine();
        System.out.println("    Oki! I've deleted this task since you said so...");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

    public void showTaskMarked(Task task, int totalTasks) {
        showLine();
        System.out.println("    You've completed a task? Good job! I have marked it as done.");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You have " + totalTasks + " scheduled tasks and events left. You got this!");
        showLine();
    }

    public void showTaskUnmarked(Task task, int totalTasks) {
        showLine();
        System.out.println("    You didn't completed the task? Please do it and stop procrastinating please...");
        System.out.println("    " + task.getTaskString());
        System.out.println("    You now have " + totalTasks + " scheduled tasks and events.");
        showLine();
    }

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


    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

}
