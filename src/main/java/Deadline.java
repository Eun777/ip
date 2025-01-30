public class Deadline extends Task {

    protected String byDate;

    public Deadline(String description, boolean isDone, String date) {
        super(description, isDone);
        this.byDate = date;
    }

    @Override
    public void printTask() {
        System.out.println("[D]["+ this.getStatusIcon()+ "] " + this.description + " (by: " + this.byDate + ")");
    }

    @Override
    public String getTaskString() {
        return "[D]["+ this.getStatusIcon()+ "] " + this.description + " (by: " + this.byDate + ")";
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
