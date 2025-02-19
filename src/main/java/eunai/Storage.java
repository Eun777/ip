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
     * @throws IOException If an I/O error occurs during writing.
     */

    public boolean saveTasks(ArrayList<Task> taskList) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writeTasksToFile(writer, taskList);
            return true; // Saving successful
        } catch (IOException e) {
            System.out.println("Error saving tasks. Your changes might not be saved.");
            return false; // Saving failed
        }
    }

    // Extracted helper method
    private void writeTasksToFile(FileWriter writer, ArrayList<Task> taskList) throws IOException {
        for (Task task : taskList) {
            writer.write(task.toFileFormat() + System.lineSeparator());
        }
    }

    /**
     * Loads previously saved tasks from the file specified by {@code filePath}.
     * If the file does not exist, an empty task list is returned.
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
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                Task task = parseTask(currLine);
                if (task != null) { // Only add valid tasks
                    prevTaskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks. Starting a new list...");
        }
        return prevTaskList;
    }

    /**
     * Parses a line from the saved file into a {@code Task} object.
     * @param line The line from the file representing a task.
     * @return The corresponding {@code Task} object.
     */
    private Task parseTask(String line) {
        String[] splitParts = line.split(" \\| ");

        if (splitParts.length < 3) {
            System.out.println("Skipping invalid task format: " + line);
            return null; // Skip bad lines instead of crashing
        }

        String taskType = splitParts[0];
        boolean taskIsDone = splitParts[1].equals("1");
        String taskDescription = splitParts[2];

        try {
            switch (taskType) {
            case "T":
                return new ToDo(taskDescription, taskIsDone);
            case "D":
                if (splitParts.length < 4) {
                    throw new IllegalArgumentException();
                }
                return new Deadline(taskDescription, taskIsDone, splitParts[3]);
            case "E":
                if (splitParts.length < 5) {
                    throw new IllegalArgumentException();
                }
                return new Event(taskDescription, taskIsDone, splitParts[3], splitParts[4]);
            default:
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing task: " + line);
            return null;
        }
    }
}
