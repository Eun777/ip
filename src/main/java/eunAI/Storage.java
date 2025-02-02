package eunAI;

import eunAI.task.Deadline;
import eunAI.task.Event;
import eunAI.task.Task;
import eunAI.task.ToDo;

import java.io.*;
import java.util.ArrayList;

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
    public void saveTasks(ArrayList<Task> taskList) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);

        for(int i = 0; i < taskList.size(); i++) {
            writer.write(taskList.get(i).toFileFormat() + System.lineSeparator());
        }

        writer.close();

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
            System.out.println("      You have not saved any information previously. Starting a new list...");
            return prevTaskList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(savedFile))) {
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                prevTaskList.add(parseTask(currLine));
            }
        } catch (IOException e) {
            System.out.println("        Error loading tasks. Starting a new list...");
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
        String taskType = splitParts[0];
        boolean taskIsDone = splitParts[1].equals("1");
        String taskDescription = splitParts[2];

        switch (taskType) {
            case "T":
                return new ToDo(taskDescription, taskIsDone);
            case "D":
                String taskDate = splitParts[3];
                return new Deadline(taskDescription, taskIsDone, taskDate);
            case "E":
                return new Event(taskDescription, taskIsDone, splitParts[3], splitParts[4]);
            default:
                throw new IllegalArgumentException("    Invalid task format.");
        }

    }
}
