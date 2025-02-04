package eunAI.task;

import com.sun.source.tree.Tree;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void printTask() {
        System.out.println("[" + this.getStatusIcon() + "] " + this.description);
    }

    public String getTaskString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public void markTask() {
        this.isDone = true;
    }

    public void unmarkTask() {
        this.isDone = false;
    }

    public boolean taskStatus() {
        return this.isDone;
    }

    public abstract String toFileFormat();

    public String getDescription() {
        return this.description;
    }
}
