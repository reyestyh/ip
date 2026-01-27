/**
 * A class to indicate an event with a specified start and end time
 */
public class EventTask extends Task {

    protected String startTime;
    protected String endTime;

    public EventTask(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String taskName,String startTime, String endTime, boolean isDone) {
        super(taskName, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String[] getTimes() {
        return new String[]{startTime, endTime};
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }

}
