package advisor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTaskTest {

    @Test
    public void test1() {
        ToDoTask temp = new ToDoTask("buy groceries");
        assertEquals("[T][ ] buy groceries", temp.toString());
        assertEquals(false, temp.isFinished());

        temp.finishTask();
        assertEquals("[T][X] buy groceries", temp.toString());
        assertEquals(true, temp.isFinished());
    }

    @Test
    public void test2() {
        ToDoTask temp = new ToDoTask("do homework", true);
        assertEquals("[T][X] do homework", temp.toString());
        assertEquals(true, temp.isFinished());

        temp.undo();
        assertEquals("[T][ ] do homework", temp.toString());
        assertEquals(false, temp.isFinished());
    }
}
