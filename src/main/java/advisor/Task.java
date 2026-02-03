package advisor;

/**
 * Base Task class containing task description and completion status
 */
public class Task {

    protected String taskName;
    protected boolean isFinished;

    /**
     * Creates a Task object with a given description
     *
     * @param taskName description of task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isFinished = false;
    }

    /**
     * Creates a Task object with a given description and completion status
     *
     * @param taskName description of task
     * @param isDone completion status of task
     */
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
     * Returns a boolean indicating if task is complete
     *
     * @return boolean indicating task completion
     */
    public boolean isFinished() {
        return this.isFinished;
    }

    public String getTaskType() {
        return "";
    }


    /**
     * Returns a description of task object
     *
     * @return String taskName description of the task
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns a string indicating if a Task is complete or not
     *
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
