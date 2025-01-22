import java.util.Scanner;
public class EunAI {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] taskList = new String[100];
        int maxIndex = -1;


        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm EunAI");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i <= maxIndex; i++) {
                    System.out.print(i + 1);
                    System.out.println(". " + taskList[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                taskList[maxIndex + 1] = input;
                maxIndex++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }
}
