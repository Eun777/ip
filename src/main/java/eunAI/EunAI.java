package eunAI;

import eunAI.command.Command;
import eunAI.command.CommandParser;
import eunAI.ui.Ui;

/**
 * Represents the main chatbot application for managing tasks.
 * This class initializes the user interface, storage system, and task list,
 * while also handling user input and executing commands.
 */
public class EunAI {

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs an instance of the EunAi chatbot with the specified file path for task storage.
     *
     * @param filePath The file path where tasks are stored and loaded from.
     */
    public EunAI(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadSavedTasks());
    }

    /**
     * Processes user input and returns the corresponding response as a string.
     * This method is primarily used for GUI interactions.
     *
     * @param input The user’s input command.
     * @return A response string generated based on the given input.
     */
    public String getResponse(String input) {
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
            System.out.println(getResponse(input));
            if (CommandParser.parseCommand(input) == CommandParser.Command.BYE) {
                isExit = true;
            }
        }
    }

}
