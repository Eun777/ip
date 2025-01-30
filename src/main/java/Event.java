public class Event extends Task {

    protected String startDate;
    protected String endDate;

    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void printTask() {
        System.out.println("[E]["+ this.getStatusIcon()+ "] " + this.description + " (from: " + this.startDate + " to: " + this.endDate + ")");
    }

    @Override
    public String getTaskString() {
        return "[E]["+ this.getStatusIcon()+ "] " + this.description + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }

    @Override
    public String toFileFormat() {
        if (this.isDone) {
            return "E | 1 | " + this.description + " | " + this.startDate + "-" + this.endDate;
        } else {
            return "E | 0 | " + this.description + " | " + this.startDate + "-" + this.endDate;
        }
    }

}
