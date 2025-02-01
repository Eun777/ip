package eunAI.task;

import eunAI.DateParser;

import java.time.LocalDateTime;
public class Deadline extends Task {

    protected LocalDateTime byDate;

    public Deadline(String description, boolean isDone, String date) {
        super(description, isDone);
        this.byDate = DateParser.parseDate(date);
    }

    @Override
    public void printTask() {
        System.out.println("[D]["+ this.getStatusIcon()+ "] " + this.description + " (by: " + DateParser.formatDate(this.byDate) + ")");
    }

    @Override
    public String getTaskString() {
        return "[D]["+ this.getStatusIcon()+ "] " + this.description + " (by: " + DateParser.formatDate(this.byDate) + ")";
    }

    @Override
    public String toFileFormat() {
        if (this.isDone) {
            return "D | 1 | " + this.description + " | " + this.byDate;
        } else {
            return "D | 0 | " + this.description + " | " + this.byDate;
        }
    }
}
