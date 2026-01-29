package advisor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to indicate an event with a specified start and end time
 */
public class EventTask extends Task {

    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public EventTask(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        this.endTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    public EventTask(String taskName, String startTime, String endTime, boolean isDone) {
        super(taskName, isDone);
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        this.endTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns Strings of start and end times in format of input
     * @return String array of start and end times
     */
    public String[] getTimesInput() {
        String start = this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        String end = this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        return new String[]{start, end};
    }

    /**
     * Returns Strings of start and end time in format of "Month Date Year Time"
     * @return String array of start and end time
     */
    public String[] getTimesStr() {
        String start = this.startTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
        String end = this.endTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
        return new String[]{start, end};
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        String[] dt = this.getTimesStr();
        return "[E]" + super.toString() + " (from: " + dt[0] + " || to: " + dt[1] + ")";
    }

}
