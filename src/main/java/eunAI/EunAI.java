package eunai;

import eunai.command.Command;
import eunai.command.CommandParser;
import eunai.ui.Ui;

/**
 * Represents the main chatbot application for managing tasks.
 * This class initializes the user interface, storage system, and task list,
 * while also handling user input and executing commands.
 */
public class EunAi {

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs an instance of the EunAi chatbot with the specified file path for task storage.
     *
     * @param filePath The file path where tasks are stored and loaded from.
     */
    public EunAi(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "filePath should not be null or empty";
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadSavedTasks());
    }

    /**
     * Processes user input and returns a response as a String.
     * Used for GUI interaction.
     *
     * @param input The user's command.
     * @return Response generated based on the command.
     */
    public String processUserInput(String input) {
        assert false : "Testing assertions";
        return Command.execute(input, taskList, ui, storage);
    }

    /**
     * Starts the chatbot in command-line interface (CLI) mode.
     * Continuously reads and processes user input until the exit command is issued.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            System.out.println(processUserInput(input));
            if (CommandParser.parseCommand(input) == CommandParser.Command.BYE) {
                isExit = true;
            }
        }
    }
}
