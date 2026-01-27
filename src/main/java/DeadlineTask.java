/**
 * A class to indicate a Task object with a deadline
 */
public class DeadlineTask extends Task {

    protected String deadline;

    public DeadlineTask(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    public DeadlineTask(String taskName, String deadline, boolean isDone) {
        super(taskName,  isDone);
        this.deadline = deadline;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
