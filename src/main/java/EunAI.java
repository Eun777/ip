import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EunAI {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int maxIndex = -1;


        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello! I'm EunAI");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("    ____________________________________________________________");
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println("    ____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("    ____________________________________________________________");
                for (int i = 0; i <= maxIndex; i++) {
                    System.out.print("      ");
                    System.out.print(i + 1);
                    System.out.print(".");
                    taskList[i].printTask();
                }
                System.out.println("    ____________________________________________________________");
            } else if (input.startsWith("mark")) {
                try {
                    // parse the task number (convert from 1-based to 0-based index)
                    int toMark = Integer.parseInt(input.substring(5).trim()) - 1;

                    // validate the index
                    if (toMark >= 0 && toMark <= maxIndex) {
                        taskList[toMark].markTask(); // mark the task as done
                        System.out.println("    ____________________________________________________________");
                        System.out.println("    Nice! I've marked this task as done:");
                        System.out.print("      ");
                        taskList[toMark].printTask();
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
            } else {
                taskList[maxIndex + 1] = new Task(input);
                maxIndex++;
                System.out.println("    ____________________________________________________________");
                System.out.println("    added: " + input);
                System.out.println("    ____________________________________________________________");
            }
        }
        scanner.close();
    }
}
