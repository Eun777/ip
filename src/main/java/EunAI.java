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
