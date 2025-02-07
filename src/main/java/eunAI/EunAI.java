package eunAI;

import eunAI.command.Command;
import eunAI.command.CommandParser;
import eunAI.ui.Ui;

/**
 * Represents the main application for managing tasks.
 * Initializes the UI, storage, and task list, and handles user input.
 */
public class EunAI {

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs an EunAI chatbot instance with the specified filePath for task storage.
     */
    public EunAI(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadSavedTasks());
    }

    /**
     * Processes user input and returns a response as a String.
     * Used for GUI interaction.
     */
    public String getResponse(String input) {
        return Command.execute(input, taskList, ui, storage);
    }

    /**
     * The entry point for CLI mode.
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
