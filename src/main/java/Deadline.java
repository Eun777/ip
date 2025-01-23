public class Deadline extends Task {

    protected String byDate;

    public Deadline(String description, String date) {
        super(description);
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
}
