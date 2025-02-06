import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import eunAI.TaskList;
import eunAI.task.Deadline;
import eunAI.task.Task;
import eunAI.task.ToDo;


public class TaskListTest {

    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("read book", false);
        taskList.addTask(todo);

        assertEquals(1, taskList.getSize());
        assertEquals("read book", taskList.getLastTask().getDescription());
    }

    @Test
    public void testFindTask() {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("read book", false));
        taskList.addTask(new Deadline("return book", false, "2024-06-01"));

        TaskList results = taskList.findTask("book");
        assertEquals(2, results.getSize());

        results = taskList.findTask("return");
        assertEquals(1, results.getSize());
        assertEquals("return book", results.getTask(0).getDescription());
    }
}
