public class Task {

    private String taskName;
    private boolean finished;

    public Task(String taskName) {
        this.taskName = taskName;
        this.finished = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public void finishTask() {
        this.finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public String toString() {
        if (this.finished) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }

}
