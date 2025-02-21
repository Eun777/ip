package eunai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import eunai.task.Deadline;
import eunai.task.Event;
import eunai.task.Task;
import eunai.task.ToDo;

/**
 * Represents the storage component responsible for reading from and writing tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a {@code Storage} object with the specified file path.
     * @param filePath The path of the file to save/load task data.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of tasks to the file specified by {@code filePath}.
     * @param taskList The list of tasks to save.
     * @return true if saving was successful; false otherwise.
     */
    public boolean saveTasks(ArrayList<Task> taskList) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writeTasksToFile(writer, taskList);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving tasks. Your changes might not be saved.");
            return false;
        }
    }

    /**
     * Writes all tasks in {@code taskList} to the given {@code FileWriter}.
     */
    private void writeTasksToFile(FileWriter writer, ArrayList<Task> taskList) throws IOException {
        for (Task task : taskList) {
            writer.write(task.toFileFormat() + System.lineSeparator());
        }
    }

    /**
     * Loads previously saved tasks from the file specified by {@code filePath}.
     * If the file does not exist, returns an empty task list.
     * @return An {@code ArrayList} of tasks loaded from the file.
     */
    public ArrayList<Task> loadSavedTasks() {
        ArrayList<Task> prevTaskList = new ArrayList<>();
        File savedFile = new File(filePath);

        if (!savedFile.exists()) {
            System.out.println("You have not saved any information previously. Starting a new list...");
            return prevTaskList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(savedFile))) {
            readTasksFromFile(reader, prevTaskList);
        } catch (IOException e) {
            System.out.println("Error loading tasks. Starting a new list...");
        }
        return prevTaskList;
    }

    /**
     * Reads tasks line by line from the given {@code BufferedReader} and adds valid tasks to {@code taskList}.
     */
    private void readTasksFromFile(BufferedReader reader, ArrayList<Task> taskList) throws IOException {
        String currLine;
        while ((currLine = reader.readLine()) != null) {
            Task task = parseTask(currLine);
            if (task != null) {
                taskList.add(task);
            }
        }
    }

    /**
     * Parses a single line from the file into a {@code Task} object.
     * @param line The line from the file.
     * @return The corresponding {@code Task} object, or null if invalid format.
     */
    private Task parseTask(String line) {
        String[] splitParts = splitTaskLine(line);
        if (splitParts == null) {
            return null;
        }
        return createTaskFromParts(splitParts, line);
    }

    /**
     * Splits the line into an array of parts and checks basic validity.
     */
    private String[] splitTaskLine(String line) {
        String[] splitParts = line.split(" \\| ");
        if (splitParts.length < 3) {
            System.out.println("Skipping invalid task format: " + line);
            return null;
        }
        return splitParts;
    }

    /**
     * Creates a {@code Task} from the parts array based on task type.
     */
    private Task createTaskFromParts(String[] splitParts, String originalLine) {
        String taskType = splitParts[0];
        boolean taskIsDone = splitParts[1].equals("1");
        String taskDescription = splitParts[2];

        try {
            switch (taskType) {
            case "T":
                return new ToDo(taskDescription, taskIsDone);
            case "D":
                return createDeadline(splitParts, taskDescription, taskIsDone);
            case "E":
                return createEvent(splitParts, taskDescription, taskIsDone);
            default:
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing task: " + originalLine);
            return null;
        }
    }

    /**
     * Creates a {@code Deadline} from the split parts array.
     */
    private Deadline createDeadline(String[] splitParts, String description, boolean isDone) {
        if (splitParts.length < 4) {
            throw new IllegalArgumentException();
        }
        return new Deadline(description, isDone, splitParts[3]);
    }

    /**
     * Creates an {@code Event} from the split parts array.
     */
    private Event createEvent(String[] splitParts, String description, boolean isDone) {
        if (splitParts.length < 5) {
            throw new IllegalArgumentException();
        }
        return new Event(description, isDone, splitParts[3], splitParts[4]);
    }
}
