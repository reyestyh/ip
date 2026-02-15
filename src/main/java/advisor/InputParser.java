package advisor;

/**
 * Class containing static methods to parse user input for Advisor
 */
public class InputParser {


    /**
     * Returns the index of the task to be deleted from user input
     * If not a number or no number inputted, return -1
     *
     * @param input user input from delete command
     * @return integer of index of Task in taskList
     */
    public static Integer deleteParser(String input) throws AdvisorException {
        int offsetFromDelete = 7;
        try {
            return Integer.parseInt(input.substring(offsetFromDelete));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new AdvisorException("An error occurred when parsing the input for 'delete'");
        }
    }

    /**
     * Returns the index of the task to be marked as complete from user input
     * If not a number or no number inputted, return -1
     *
     * @param input user input from mark command
     * @return integer of index of Task in taskList
     */
    public static Integer markParser(String input) throws AdvisorException {
        int offsetFromMark = 5;
        try {
            return Integer.parseInt(input.substring(offsetFromMark));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new AdvisorException("An error occurred when parsing the input for 'mark'");
        }
    }

    /**
     * Returns the index of the task to be marked as incomplete from user input
     * If not a number or no number inputted, return -1
     *
     * @param input user input from unmark command
     * @return integer of index of Task in taskList
     */
    public static Integer unmarkParser(String input) throws AdvisorException {
        int offsetFromUnmark = 7;
        try {
            return Integer.parseInt(input.substring(offsetFromUnmark));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new AdvisorException("An error occurred when parsing the input for 'unmark'");
        }
    }

    /**
     * Returns the description of the task to be added from command todo
     *
     * @param input user input from todo command
     * @return description of task
     */
    public static String todoParser(String input) throws AdvisorException {
        int offsetFromTodo = 5;
        try {
            String desc = input.substring(offsetFromTodo);
            return desc;
        } catch (StringIndexOutOfBoundsException e) {
            throw new AdvisorException("No description passed");
        }
    }

    /**
     * Returns array of strings containing the description of the task and task deadline from
     * a deadline task
     * index 0: task description
     * index 1: deadline
     *
     * @param input user input from deadline command
     * @return String array of task data
     */
    public static String[] deadlineParser(String input) {
        int offsetFromDeadline = 9;
        int descriptionIndex = 0;
        int deadlineIndex = 1;
        int correctInfoArraySize = 2;

        String temp = "";
        try {
            temp = input.substring(offsetFromDeadline);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }


        String[] deadlineTaskInfo = temp.split("/by");
        if (deadlineTaskInfo.length != correctInfoArraySize) {
            return null;
        }

        try {
            deadlineTaskInfo[descriptionIndex] = deadlineTaskInfo[descriptionIndex].strip();
            deadlineTaskInfo[deadlineIndex] = deadlineTaskInfo[deadlineIndex].strip();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        if (deadlineTaskInfo[descriptionIndex].isEmpty() || deadlineTaskInfo[deadlineIndex].isEmpty()) {
            return null;
        }

        return deadlineTaskInfo;
    }


    /**
     * Returns array of strings containing the description of the task, start time and end time from
     * an event task
     * index 0: task description
     * index 1: start time
     * index 2: end time
     *
     * @param input user input from event command
     * @return String array of task data
     */
    public static String[] eventParser(String input) {
        int offsetFromEvent = 6;
        int descriptionIndex = 0;

        int timesStringIndex = 1;

        int startTimeIndex = 0;
        int endTimeIndex = 1;

        int correctInfoArraySize = 2;

        String[] temp = null;
        try {
            temp = input.substring(offsetFromEvent).split(" /from ");
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }

        String taskDesc = temp[descriptionIndex];
        if (temp.length != correctInfoArraySize) {
            return null;
        }

        String[] times = null;

        // temp[1] is "<start time> /to <end time>"
        times = temp[timesStringIndex].split(" /to ");


        if (times.length != correctInfoArraySize) {
            return null;
        }

        String startTime = times[startTimeIndex];
        String endTime = times[endTimeIndex];

        return new String[]{taskDesc, startTime, endTime};
    }

    /**
     * Returns the search term used for the find command
     *
     * @param input user input from find command
     * @return String search term
     */
    public static String findParser(String input) throws AdvisorException {
        int offsetFromFind = 5;

        try {
            // ChatGPT caught a possible edge case and suggested a fix
            String searchTerm = input.substring(offsetFromFind).strip();
            if (searchTerm.isEmpty()) {
                throw new AdvisorException("Invalid format");
            }
            return searchTerm;

        } catch (StringIndexOutOfBoundsException e) {
            throw new AdvisorException("No search term found");
        }
    }

}
