package eunai.task;

/**
 * Represents a simple to-do task without any specific date or time.
 * Inherits from the {@link Task} class and provides methods to display and store the task.
 */
public class ToDo extends Task {

    /**
     * Constructs a {@code ToDo} task with the specified description and completion status.
     *
     * @param description The description of the to-do task.
     * @param isDone Indicates whether the to-do task is marked as done.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Prints the to-do task details to the console.
     * The format is {@code [T][status] description}.
     */
    @Override
    public void printTask() {
        System.out.println("[T][" + this.getStatusIcon() + "] " + this.description);
    }

    /**
     * Returns the string representation of the to-do task for display purposes.
     *
     * @return A string representing the to-do task in the format {@code [T][status] description}.
     */
    @Override
    public String getTaskString() {
        return "[T][" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns the string representation of the to-do task formatted for file storage.
     *
     * @return A string representing the to-do task in the format
     *         {@code T | 1/0 | description}, where {@code 1} indicates a completed task
     *         and {@code 0} indicates an incomplete task.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }
    @Override
    public String getTaskType() {
        return "T";
    }

}
