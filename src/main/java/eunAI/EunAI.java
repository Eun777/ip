package eunAI;

import eunAI.command.Command;
import eunAI.command.CommandParser;
import eunAI.ui.Ui;

import java.util.Scanner;

/**
 * Represents the main application for managing tasks.
 * Initializes the UI, storage, and task list, and handles user input in a command loop.
 */
public class EunAI {

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Contructs an EunAI chatbot instance with the specified filePath for task storage.
     * @param filePath The file path where tasks are saved and loaded.
     */
    public EunAI(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadSavedTasks());
    }

    /**
     * Runs the main loop of the application.
     * Displays a welcome message and processes user commands until the exit command is received.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            String input = ui.readCommand();
            CommandParser.Command commandType = CommandParser.parseCommand(input);

            Command.execute(input, taskList, ui, storage);

            if (commandType == CommandParser.Command.BYE) {
                isExit = true;
            }
        }
    }

    /**
     * The entry point of the application.
     * Initializes and runs an EunAI instance using the default file path.
     * @param args
     */
    public static void main(String[] args) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + "/ip/data/eunAI.txt";
        new EunAI(filePath).run();
    }
}
