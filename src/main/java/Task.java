public class Task {

    protected enum TaskStatus {
        NOT_DONE,
        IN_PROGRESS,
        COMPLETED,
    }

    protected String taskName;
    protected TaskStatus finished;

    public Task(String taskName) {
        this.taskName = taskName;
        this.finished = TaskStatus.NOT_DONE;
    }

    public void finishTask() {
        this.finished = TaskStatus.COMPLETED;
    }

    public void undo() {
        this.finished = TaskStatus.NOT_DONE;
    }

    public boolean isFinished() {
        return this.finished == TaskStatus.COMPLETED;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return isFinished() ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getStatus() + "] " + this.taskName;
    }

}
