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
    public static Integer deleteParser(String input) {
        try {
            return Integer.parseInt(input.substring(7));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * Returns the index of the task to be marked as complete from user input
     * If not a number or no number inputted, return -1
     *
     * @param input user input from mark command
     * @return integer of index of Task in taskList
     */
    public static Integer markParser(String input) {
        try {
            return Integer.parseInt(input.substring(5));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * Returns the index of the task to be marked as incomplete from user input
     * If not a number or no number inputted, return -1
     *
     * @param input user input from unmark command
     * @return integer of index of Task in taskList
     */
    public static Integer unmarkParser(String input) {
        try {
            return Integer.parseInt(input.substring(7));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * Returns the description of the task to be added from command todo
     *
     * @param input user input from todo command
     * @return description of task
     */
    public static String todoParser(String input) {
        try {
            return input.substring(5);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
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
        String[] tt = temp.split("/by");
        if (tt.length != 2) {
            return null;
        }
        try {
            tt[0] = tt[0].strip();
            tt[1] = tt[1].strip();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        if (tt[0].isEmpty() || tt[1].isEmpty()) {
            return null;
        }
        return tt;
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

    public static String findParser(String input) {
        try {
            return input.substring(5);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

}
