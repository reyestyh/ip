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
        try {
            assert(!input.substring(7).isEmpty());
            return Integer.parseInt(input.substring(7));
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
        try {
            assert(!input.substring(5).isEmpty());
            return Integer.parseInt(input.substring(5));
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
        try {
            assert(!input.substring(7).isEmpty());
            return Integer.parseInt(input.substring(7));
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
        try {
            String desc = input.substring(5);
            assert(!desc.isEmpty());
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
        String temp = "";
        try {
            temp = input.substring(9);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        assert(!temp.isEmpty());

        String[] deadlineTaskInfo = temp.split("/by");

        if (deadlineTaskInfo.length != 2) {
            return null;
        }
        assert(deadlineTaskInfo.length == 2);

        try {
            deadlineTaskInfo[0] = deadlineTaskInfo[0].strip();
            deadlineTaskInfo[1] = deadlineTaskInfo[1].strip();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        if (deadlineTaskInfo[0].isEmpty() || deadlineTaskInfo[1].isEmpty()) {
            return null;
        }

        assert(!deadlineTaskInfo[0].isEmpty());
        assert(!deadlineTaskInfo[1].isEmpty());

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
        String[] temp = null;
        try {
            temp = input.substring(6).split(" /from ");
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
        assert(!temp[0].isEmpty());

        String taskDesc = temp[0];
        if (temp.length != 2) {
            return null;
        }

        String[] times = null;
        try {
            times = temp[1].split(" /to ");
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        assert(!times[0].isEmpty());
        assert(!times[1].isEmpty());

        if (times.length != 2) {
            return null;
        }

        String startTime = times[0];
        String endTime = "";

        try {
            endTime = times[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new String[]{taskDesc, startTime, endTime};
    }

    /**
     * Returns the search term used for the find command
     *
     * @param input user input from find command
     * @return String search term
     */
    public static String findParser(String input) throws AdvisorException {
        try {
            String searchTerm = input.substring(5);
            assert(!searchTerm.isEmpty());
            return searchTerm;
        } catch (StringIndexOutOfBoundsException e) {
            throw new AdvisorException("No search term found");
        }
    }

}
