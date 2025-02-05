package eunAI.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eunAI.DateParser;

/**
 * Represents an event task that occurs within a specific time period.
 * Inherits from the {@link Task} class and includes start and end dates for the event.
 */
public class Event extends Task {

    /** The start date and time of the event. */
    protected LocalDateTime startDate;

    /** The end date and time of the event. */
    protected LocalDateTime endDate;

    /**
     * Constructs an {@code Event} task with the specified description, completion status, start date, and end date.
     *
     * @param description The description of the event task.
     * @param isDone Indicates whether the event task is marked as done.
     * @param startDate The start date of the event in string format, which will be parsed into a {@code LocalDateTime}.
     * @param endDate The end date of the event in string format, which will be parsed into a {@code LocalDateTime}.
     */
    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description, isDone);
        this.startDate = DateParser.parseDate(startDate);
        this.endDate = DateParser.parseDate(endDate);
    }

    /**
     * Prints the event task details to the console.
     * The format is {@code [E][status] description (from: start date to: end date)}.
     */
    @Override
    public void printTask() {
        System.out.println("[E][" + this.getStatusIcon() + "] " + this.description
                + " (from: " + DateParser.formatDate(this.startDate)
                + " to: " + DateParser.formatDate(this.endDate) + ")");
    }

    /**
     * Returns the string representation of the event task for display purposes.
     *
     * @return A string representing the event task in the format
     *         {@code [E][status] description (from: start date to: end date)}.
     */
    @Override
    public String getTaskString() {
        return "[E][" + this.getStatusIcon() + "] " + this.description
                + " (from: " + DateParser.formatDate(this.startDate)
                + " to: " + DateParser.formatDate(this.endDate) + ")";
    }

    /**
     * Returns the string representation of the event task formatted for file storage.
     *
     * @return A string representing the event task in the format
     *         {@code E | 1/0 | description | start date | end date}, where {@code 1} indicates a completed task
     *         and {@code 0} indicates an incomplete task. The dates are stored in ISO format.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | "
                + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
