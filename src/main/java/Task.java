public class Task {

    protected String taskName;
    protected boolean finished;

    public Task(String taskName) {
        this.taskName = taskName;
        this.finished = false;
    }

    public void finishTask() {
        this.finished = true;
    }

    public void undo() {
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return finished ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + this.getStatus() + "] " + this.taskName;
    }

}
