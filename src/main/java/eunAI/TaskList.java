package eunAI;

import java.util.ArrayList;

import eunAI.task.Task;

/**
 * Represents a list of tasks.
 * Provides methods to add, delete, search, and modify tasks in the list.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Constructs an empty {@code TaskList}.
     * This is typically used when starting a new task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} initialized with a given list of stored tasks.
     *
     * @param storedTasks The list of tasks to initialize the task list with.
     */
    public TaskList(ArrayList<Task> storedTasks) {
        this.taskList = storedTasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Deletes the task at the specified index from the task list.
     *
     * @param index The index of the task to be deleted (0-based).
     */
    public void deleteTask(int index) {
        this.taskList.remove(index);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task to retrieve (0-based).
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return this.taskList.get(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index The index of the task to mark as done (0-based).
     */
    public void markTask(int index) {
        this.taskList.get(index).markTask();
    }

    /**
     * Unmarks the task at the specified index, marking it as not done.
     *
     * @param index The index of the task to unmark (0-based).
     */
    public void unmarkTask(int index) {
        this.taskList.get(index).unmarkTask();
    }

    /**
     * Retrieves the full list of tasks.
     *
     * @return An {@code ArrayList} containing all tasks in the list.
     */
    public ArrayList<Task> getAllTasks() {
        return this.taskList;
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The total number of tasks in the list.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Retrieves the last task in the task list.
     *
     * @return The last task in the list.
     * @throws IndexOutOfBoundsException If the task list is empty.
     */
    public Task getLastTask() {
        return taskList.get(getSize() - 1);
    }

    /**
     * Searches for tasks containing the specified keyword in their description.
     *
     * @param keyword The keyword to search for (case-insensitive).
     * @return A {@code TaskList} containing tasks that match the search keyword.
     */
    public TaskList findTask(String keyword) {
        TaskList foundTasks = new TaskList();
        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                foundTasks.addTask(task);
            }
        }
        return foundTasks;
    }

    /**
     * Returns a formatted string representation of the task list.
     *
     * @return A formatted string listing all tasks, or a message if the list is empty.
     */
    public String getListString() {
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }
        StringBuilder listString = new StringBuilder("Here are your tasks:\n");

        for (int i = 0; i < taskList.size(); i++) {
            listString.append(i + 1).append(". ").append(taskList.get(i).getTaskString()).append("\n");
        }
        return listString.toString().trim();
    }
}
