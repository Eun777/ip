public class Event extends Task {

    protected String startDate;
    protected String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
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

}
