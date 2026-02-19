package advisor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to indicate an event with a specified start and end time
 */
public class EventTask extends Task {

    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    /**
     * Creates EventTask object with a given description, start and end times for the event
     *
     * @param taskName  description of task
     * @param startTime start time of event in format "yyyy-MM-dd HHmm"
     * @throws AdvisorException when end time is before start time
     */
    public EventTask(String taskName, String startTime, String endTime) throws AdvisorException {
        super(taskName);
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        this.endTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

        if (this.endTime.isBefore(this.startTime)) {
            throw new AdvisorException("End time before start time");
        }

    }

    /**
     * Creates EventTask object with a given description, start and end times for the event, and task completion status
     *
     * @param taskName  description of task
     * @param startTime start time of event in format "yyyy-MM-dd HHmm"
     * @param endTime   end time of event in format "yyyy-MM-dd HHmm"
     * @param isDone    completion status of task
     * @throws AdvisorException when end time is before start time
     */
    public EventTask(String taskName, String startTime, String endTime, boolean isDone) throws AdvisorException {
        super(taskName, isDone);
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        this.endTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

        if (this.endTime.isBefore(this.startTime)) {
            throw new AdvisorException("End time before start time");
        }

    }

    /**
     * Returns Strings of start and end times in format of input
     *
     * @return String array of start and end times
     */
    public String[] getTimesInput() {
        String start = this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        String end = this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        return new String[]{start, end};
    }

    /**
     * Returns Strings of start and end time in format of "Month Date Year Time"
     *
     * @return String array of start and end time
     */
    public String[] getTimesStr() {
        String start = this.startTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
        String end = this.endTime.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"));
        return new String[]{start, end};
    }

    /**
     * Returns start time of Event task
     *
     * @return LocalDateTime start time of event
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Returns end time of Event task
     *
     * @return LocalDateTime end time of event
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
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

    @Override
    public int compareTo(Task toCompare) {
        if (toCompare.equals(this)) {
            return 0;
        }

        if (!(toCompare instanceof EventTask)) {
            return 1;
        }

        int startsEarlier = this.startTime.compareTo(((EventTask) toCompare).getStartTime());
        int endsEarlier = this.endTime.compareTo(((EventTask) toCompare).getEndTime());

        if (startsEarlier != 0) {
            return startsEarlier;
        }

        if (endsEarlier != 0) {
            return endsEarlier;
        }

        return this.getTaskName().compareTo(toCompare.getTaskName());


    }

}
