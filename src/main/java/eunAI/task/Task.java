package eunAI.task;

// JavaDocs for eunAI.task package generated by ChatGPT

/**
 * Represents an abstract task with a description and completion status.
 * Provides common functionalities for all types of tasks such as marking, unmarking, and displaying task information.
 * Subclasses must implement the {@link #toFileFormat()} method for file storage.
 */
public abstract class Task {

    /** The description of the task. */
    protected String description;

    /** The completion status of the task. {@code true} if the task is done, {@code false} otherwise. */
    protected boolean isDone;

    /**
     * Constructs a {@code Task} with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone Indicates whether the task is marked as done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of the task.
     * <p>
     * {@code "X"} if the task is done, otherwise a blank space {@code " "}.
     * </p>
     *
     * @return The status icon representing whether the task is completed.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Prints the task details to the console.
     * The format is {@code [status] description}.
     */
    public void printTask() {
        System.out.println("[" + this.getStatusIcon() + "] " + this.description);
    }

    /**
     * Returns the string representation of the task for display purposes.
     *
     * @return A string representing the task in the format {@code [status] description}.
     */
    public String getTaskString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Marks the task as done.
     */
    public void markTask() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkTask() {
        this.isDone = false;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean taskStatus() {
        return this.isDone;
    }

    /**
     * Returns the string representation of the task formatted for file storage.
     * This method must be implemented by subclasses.
     *
     * @return A string representing the task in a format suitable for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Returns the task type identifier.
     *
     * @return A string representing the task type ("T" for ToDo, "D" for Deadline, "E" for Event).
     */
    public String getTaskType() {
        return "default"; // Default, should be overridden in subclasses
    }

}
