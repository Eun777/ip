public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    @Override
    public void printTask() {
        System.out.println("[T]["+ this.getStatusIcon()+ "] " + this.description);
    }
    @Override
    public String getTaskString() {
        return "[T]["+ this.getStatusIcon()+ "] " + this.description;
    }

}
