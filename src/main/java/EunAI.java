import java.util.Scanner;

public class EunAI {

    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    public EunAI(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadSavedTasks());
    }

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

    public static void main(String[] args) {
        new EunAI("./data/eunAI.txt").run();
    }
}
