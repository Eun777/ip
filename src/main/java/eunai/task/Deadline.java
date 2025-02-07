package eunai.task;

import java.time.LocalDateTime;

import eunai.DateParser;

/**
 * Represents a task with a deadline.
 * Inherits from the {@link Task} class and includes a due date for completion.
 */
public class Deadline extends Task {

    /** The date and time by which the task should be completed. */
    protected LocalDateTime byDate;

    /**
     * Constructs a {@code Deadline} task with the specified description, completion status, and due date.
     *
     * @param description The description of the deadline task.
     * @param isDone Indicates whether the task is marked as done.
     * @param date The due date of the task in string format, which will be parsed into a {@code LocalDateTime}.
     */
    public Deadline(String description, boolean isDone, String date) {
        super(description, isDone);
        this.byDate = DateParser.parseDate(date);
    }

    /**
     * Prints the deadline task details to the console.
     * The format is {@code [D][status] description (by: due date)}.
     */
    @Override
    public void printTask() {
        System.out.println("[D][" + this.getStatusIcon() + "] " + this.description
                + " (by: " + DateParser.formatDate(this.byDate) + ")");
    }

    /**
     * Returns the string representation of the deadline task for display purposes.
     *
     * @return A string representing the deadline task in the format
     *         {@code [D][status] description (by: due date)}.
     */
    @Override
    public String getTaskString() {
        return "[D][" + this.getStatusIcon() + "] " + this.description
                + " (by: " + DateParser.formatDate(this.byDate) + ")";
    }

    /**
     * Returns the string representation of the deadline task formatted for file storage.
     *
     * @return A string representing the deadline task in the format
     *         {@code D | 1/0 | description | due date}, where {@code 1} indicates a completed task
     *         and {@code 0} indicates an incomplete task.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.byDate;
    }
}
