public class CommandParser {
    public enum Command {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, BYE, INVALID
    }

    public static Command parseCommand(String input) {
        if (input.startsWith("todo")) {
            return Command.TODO;
        } else if (input.startsWith("deadline")) {
            return Command.DEADLINE;
        } else if (input.startsWith("event")) {
            return Command.EVENT;
        } else if (input.equals("list")) {
            return Command.LIST;
        } else if (input.startsWith("mark")) {
            return Command.MARK;
        } else if (input.startsWith("unmark")) {
            return Command.UNMARK;
        } else if (input.startsWith("delete")) {
            return Command.DELETE;
        } else if (input.equals("bye")) {
            return Command.BYE;
        } else {
            return Command.INVALID;
        }
    }
}
