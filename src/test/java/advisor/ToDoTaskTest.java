package advisor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTaskTest {

    @Test
    public void todoTaskCreation_sampleTask_correctFormat() {
        ToDoTask temp = new ToDoTask("buy groceries");
        assertEquals("[T][ ] buy groceries", temp.toString());
        assertEquals(false, temp.isFinished());

    }

    @Test
    public void todoTaskCreation_sampleTaskMark_correctFormat() {
        ToDoTask temp = new ToDoTask("buy groceries");
        temp.finishTask();
        assertEquals("[T][X] buy groceries", temp.toString());
        assertEquals(true, temp.isFinished());
    }

    @Test
    public void todoTask_unmarkTask_correctOutput() {
        ToDoTask temp = new ToDoTask("do homework", true);

        temp.undo();
        assertEquals("[T][ ] do homework", temp.toString());
        assertEquals(false, temp.isFinished());
    }
}
