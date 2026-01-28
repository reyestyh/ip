package duke;

/**
 * Base Task class containing task description and completion status
 */
public class Task {

    protected String taskName;
    protected boolean isFinished;

    /**
     * Constructor for Task object
     * @param taskName description of task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isFinished = false;
    }

    public Task(String taskName, boolean isDone) {
        this.taskName = taskName;
        this.isFinished = isDone;
    }

    /**
     * Marks Task object as complete
     */
    public void finishTask() {
        this.isFinished = true;
    }

    /**
     * Marks Task object as incomplete
     */
    public void undo() {
        this.isFinished = false;
    }


    /**
     * @return boolean indicating task completion
     */
    public boolean isFinished() {
        return this.isFinished;
    }

    public String getTaskType() {
        return "";
    }


    /**
     * @return String taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @return String of either "X" or an empty string to indicate task completion
     */
    public String getStatusAsString() {
        return isFinished() ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getStatusAsString() + "] " + this.taskName;
    }

}
