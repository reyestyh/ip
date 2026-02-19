package advisor;

/**
 * Class containing static methods to parse user input for Advisor
 */
public class InputParser {


    /**
     * Returns the index of the task to perform the specified command on
     * If error occurs during parsing, throw an AdvisorException
     *
     * @param input user input from delete, mark, or unmark
     * @param command command user is executing
     * @return Integer index of task to be edited
     * @throws AdvisorException when no number is in the input, or a non-number is in the input
     */

    public static Integer commandIndexParser(String input, String command) throws AdvisorException {
        int correctInfoArraySize = 2;
        int specifiedIdx = 1;


        String[] splitByCmd = input.split(command);

        if (splitByCmd.length != correctInfoArraySize) {
            throw new AdvisorException("An error occurred when parsing the input for '" + command + "'");
        }
        String indexStr = splitByCmd[specifiedIdx].trim();

        try {
            return Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new AdvisorException("An error occurred when parsing the input for '" + command + "'");
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
    public static String[] deadlineParser(String input) throws AdvisorException {
        int deadlineStringLength = 8;

        int offsetFromDeadline = input.indexOf("deadline") + deadlineStringLength;
        int descriptionIndex = 0;
        int deadlineIndex = 1;
        int correctInfoArraySize = 2;

        String temp = "";
        try {
            temp = input.substring(offsetFromDeadline);
        } catch (StringIndexOutOfBoundsException e) {
            throw new AdvisorException("Invalid format");
        }


        String[] deadlineTaskInfo = temp.split("/by");
        if (deadlineTaskInfo.length != correctInfoArraySize) {
            throw new AdvisorException("Invalid format");
        }

        try {
            deadlineTaskInfo[descriptionIndex] = deadlineTaskInfo[descriptionIndex].strip();
            deadlineTaskInfo[deadlineIndex] = deadlineTaskInfo[deadlineIndex].strip();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AdvisorException("Invalid format");
        }

        if (deadlineTaskInfo[descriptionIndex].isEmpty() || deadlineTaskInfo[deadlineIndex].isEmpty()) {
            throw new AdvisorException("Invalid format");
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
    public static String[] eventParser(String input) throws AdvisorException {
        int eventStringLength = 5;
        int offsetFromEvent = input.indexOf("event") + eventStringLength;

        int descriptionIndex = 0;

        int timesStringIndex = 1;

        int startTimeIndex = 0;
        int endTimeIndex = 1;

        int correctInfoArraySize = 2;

        String[] temp = null;
        try {
            temp = input.substring(offsetFromEvent).split(" /from ");
        } catch (StringIndexOutOfBoundsException e) {
            throw new AdvisorException("Invalid format");
        }

        String taskDesc = temp[descriptionIndex];
        if (temp.length != correctInfoArraySize) {
            throw new AdvisorException("Invalid format");
        }

        String[] times = null;

        // temp[1] is "<start time> /to <end time>"
        times = temp[timesStringIndex].split(" /to ");


        if (times.length != correctInfoArraySize) {
            throw new AdvisorException("Invalid format");
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
