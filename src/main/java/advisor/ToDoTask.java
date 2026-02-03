package advisor;

/**
 * A class to indicate a To do task
 */
public class ToDoTask extends Task {

    /**
     * Creates a ToDoTask object with a given description
     *
     * @param taskName description of task
     */
    public ToDoTask(String taskName) {
        super(taskName);
    }

    /**
     * Creates a ToDoTask object with a given description and completion status
     *
     * @param taskName description of task
     * @param isDone completion status of task
     */
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
