import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Event extends Task {

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description, isDone);
        this.startDate = DateParser.parseDate(startDate);
        this.endDate = DateParser.parseDate(endDate);
    }

    @Override
    public void printTask() {
        System.out.println("[E]["+ this.getStatusIcon()+ "] " + this.description + " (from: " + DateParser.formatDate(this.startDate) + " to: " + DateParser.formatDate(this.endDate) + ")");
    }

    @Override
    public String getTaskString() {
        return "[E]["+ this.getStatusIcon()+ "] " + this.description + " (from: " + DateParser.formatDate(this.startDate) + " to: " + DateParser.formatDate(this.endDate) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | "
                + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


}
