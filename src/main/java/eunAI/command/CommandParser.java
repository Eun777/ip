package eunAI.command;

/**
 * Parses user input and identifies the corresponding command type.
 * Provides functionality to map raw input strings to predefined command types.
 */
public class CommandParser {

    /**
     * Represents the list of supported commands in the application.
     */
    public enum Command {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, FIND, BYE, INVALID
    }

    /**
     * Parses the user's input string and returns the corresponding {@link Command} type.
     * <p>
     * If the input does not match any known command, {@code Command.INVALID} is returned.
     * </p>
     *
     * @param input The user input string to be parsed.
     * @return The corresponding {@link Command} type based on the input.
     */
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
        } else if (input.startsWith("find")) {
            return Command.FIND;
        } else if (input.equals("bye")) {
            return Command.BYE;
        } else {
            return Command.INVALID;
        }
    }
}
