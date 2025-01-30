public class ToDo extends Task {

    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public void printTask() {
        System.out.println("[T]["+ this.getStatusIcon()+ "] " + this.description);
    }
    @Override
    public String getTaskString() {
        return "[T]["+ this.getStatusIcon()+ "] " + this.description;
    }

    @Override
    public String toFileFormat() {
        if (this.isDone) {
            return "T | 1 | " + this.description;
        } else {
            return "T | 0 | " + this.description;
        }
    }

}
