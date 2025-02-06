import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import eunAI.task.Deadline;
import eunAI.DateParser;

public class DeadlineTest {

    @Test
    public void testGetTaskStringNotDone() {
        Deadline deadline = new Deadline("submit assignment", false, "2024-06-01 18:00");

        String expected = "[D][ ] submit assignment (by: Jun 1, 2024 06:00 PM)";
        assertEquals(expected, deadline.getTaskString());
    }

    @Test
    public void testGetTaskStringDone() {
        Deadline deadline = new Deadline("submit assignment", true, "2024-06-01 18:00");

        String expected = "[D][X] submit assignment (by: Jun 1, 2024 06:00 PM)";
        assertEquals(expected, deadline.getTaskString());
    }

    @Test
    public void testToFileFormatNotDone() {
        Deadline deadline = new Deadline("submit assignment", false, "2024-06-01 18:00");

        String expected = "D | 0 | submit assignment | 2024-06-01T18:00";
        assertEquals(expected, deadline.toFileFormat());
    }

    @Test
    public void testToFileFormatDone() {
        Deadline deadline = new Deadline("submit assignment", true, "2024-06-01 18:00");

        String expected = "D | 1 | submit assignment | 2024-06-01T18:00";
        assertEquals(expected, deadline.toFileFormat());
    }
}
