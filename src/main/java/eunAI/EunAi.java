package eunAI;

import eunAI.command.Command;
import eunAI.command.CommandParser;
import eunAI.ui.Ui;

/**
 * Represents the main application for managing tasks.
 * Initializes the UI, storage, and task list, and handles user input.
 */
public class EunAi {

    private static final CommandParser.Command EXIT_COMMAND = CommandParser.Command.BYE;

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs an instance of EunAi chatbot with the given file path for task storage.
     *
     * @param filePath Path to the storage file where tasks are saved.
     */
    public EunAi(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.loadSavedTasks());
    }

    /**
     * Processes user input and returns a response as a String.
     * Used for GUI interaction.
     *
     * @param input The user's command.
     * @return Response generated based on the command.
     */
    public String processUserInput(String input) {
        return Command.execute(input, taskList, ui, storage);
    }

    /**
     * The entry point for CLI mode.
     * Continuously reads and processes user input until the exit command is given.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            System.out.println(processUserInput(input));
            isExit = shouldExit(input);
        }
    }

    /**
     * Determines if the user wants to exit the application.
     *
     * @param input The user's command.
     * @return True if the command is an exit command, false otherwise.
     */
    private boolean shouldExit(String input) {
        return CommandParser.parseCommand(input) == EXIT_COMMAND;
    }
}
