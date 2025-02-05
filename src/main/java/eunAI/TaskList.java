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
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} with the given list of stored tasks.
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
     * @param index The index of the task to be deleted.
     */
    public void deleteTask(int index) {
        this.taskList.remove(index);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return this.taskList.get(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        this.taskList.get(index).markTask();
    }

    /**
     * Unmarks the task at the specified index (marks it as not done).
     *
     * @param index The index of the task to unmark.
     */
    public void unmarkTask(int index) {
        this.taskList.get(index).unmarkTask();
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return The list of all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return this.taskList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Retrieves the last task in the task list.
     *
     * @return The last task in the list.
     */
    public Task getLastTask() {
        return taskList.get(getSize() - 1);
    }

    /**
     * Searches for tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for in the task descriptions.
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
}
