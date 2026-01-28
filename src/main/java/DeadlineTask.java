import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to indicate a Task object with a deadline
 */
public class DeadlineTask extends Task {

    protected LocalDateTime deadline;

    public DeadlineTask(String taskName, String deadline) {
        super(taskName);
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    public DeadlineTask(String taskName, String deadline, boolean isDone) {
        super(taskName, isDone);
        this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    public String getDeadlineInput() {
        return deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    public String getDeadlineString() {
        return this.deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineString() + ")";
    }
}
