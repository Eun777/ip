import java.io.*;
import java.util.ArrayList;
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> taskList) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);

        for(int i = 0; i < taskList.size(); i++) {
            writer.write(taskList.get(i).toFileFormat() + System.lineSeparator());
        }

        writer.close();

    }

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
