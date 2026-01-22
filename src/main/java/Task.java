/**
 * Base Task class containing task description and completion status
 */
public class Task {

    protected enum TaskStatus {
        NOT_DONE,
        IN_PROGRESS,
        COMPLETED,
    }

    protected String taskName;
    protected TaskStatus finished;

    /**
     * Constructor for Task object
     * @param taskName description of task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.finished = TaskStatus.NOT_DONE;
    }

    /**
     * Marks Task object as complete
     */
    public void finishTask() {
        this.finished = TaskStatus.COMPLETED;
    }

    /**
     * Marks Task object as incomplete
     */
    public void undo() {
        this.finished = TaskStatus.NOT_DONE;
    }


    /**
     * @return boolean indicating task completion
     */
    public boolean isFinished() {
        return this.finished == TaskStatus.COMPLETED;
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
