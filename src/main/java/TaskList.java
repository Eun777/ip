import java.util.ArrayList;
public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList () {
        this.taskList = new ArrayList<>();
    }

    public TaskList (ArrayList<Task> storedTasks) {
        this.taskList = storedTasks;
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void deleteTask(int index) {
        this.taskList.remove(index);
    }

    public Task getTask(int index) {
        return this.taskList.get(index);
    }

    public void markTask(int index) {
        this.taskList.get(index).markTask();
    }

    public void unmarkTask(int index) {
        this.taskList.get(index).unmarkTask();
    }

    public ArrayList<Task> getAllTasks() {
        return this.taskList;
    }

    public int getSize() {
        return taskList.size();
    }

    public Task getLastTask() {
        return taskList.getLast();
    }
}
