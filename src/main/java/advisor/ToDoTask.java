package advisor;

/**
 * A class to indicate a To do task
 */
public class ToDoTask extends Task {

    public ToDoTask(String taskName) {
        super(taskName);
    }

    public ToDoTask(String taskName, boolean isDone) {
        super(taskName, isDone);
    }

    @Override
    public String getTaskType() {
        return "T";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }


}
