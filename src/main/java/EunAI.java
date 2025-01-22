import java.util.Scanner;
public class EunAI {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


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
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(input);
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }
}
