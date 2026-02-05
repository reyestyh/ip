package advisor;

import java.util.List;
import java.util.Scanner;

/**
 * Class containing methods to show interface to user
 */
public class Ui {

    private final String logo = "            _       _                \n"
            + "           | |     (_)               \n"
            + "   __ _  __| |_   ___ ___  ___  _ __ \n"
            + "  / _` |/ _` \\ \\ / / / __|/ _ \\| '__|\n"
            + " | (_| | (_| |\\ V /| \\__ \\ (_) | |   \n"
            + "  \\__,_|\\__,_| \\_/ |_|___/\\___/|_|   \n"
            + "                                     \n"
            + "                                     ";
    private final String name = "Advisor";
    private final String line = "____________________________________________________________";
    private Scanner input;

    /**
     * Constructs Ui instance for methods to get Advisor response messages
     */
    public Ui() {
        this.input = new Scanner(System.in);
    }

    /**
     * Returns the user inputted command from raw input
     *
     * @param input String user's raw input
     * @return String the command from user
     */
    public String readCommand(String input) {
        return input.split(" ")[0];
    }

    /**
     * Returns startup logo and welcome message
     */
    public String getStartupMessage() {
        StringBuilder response = new StringBuilder();
        response.append("Hello. I am ").append(name).append("\n");
        response.append("What do you want me to do?");
        return response.toString();
    }

    /**
     * Returns exit message
     */
    public String getExitMessage() {
        return "End of Session. Goodbye!";
    }

    /**
     * Prints prompt for user input, and returns a stripped input from user
     *
     * @return String (stripped) of user input to interface
     */
    public String readInput() {
        System.out.println("Enter a command:");
        return input.nextLine().strip();
    }

    /**
     * Returns success/failure message for updating data file AdvisorTaskData.txt
     *
     * @param isSuccessfulUpdate boolean of whether file was updated successfully
     */
    public String getUpdateFileMessage(boolean isSuccessfulUpdate) {
        return isSuccessfulUpdate ? "Data file successfully updated.\n"
                : "An error occurred while updating the data file.\n";
    }

    /**
     * Returns current tasks in TaskList as a string
     *
     * @param taskList TaskList object storing tasks in current session
     */
    public String getCurrentTasks(TaskList taskList) {
        return taskList.getTasksString();
    }

    /**
     * Returns error message for invalid command
     */
    public String getInvalidCommandMessage() {
        return "Invalid command. Try again.";
    }

    /**
     * Returns message for new task added from todo, deadline, or event commands
     *
     * @param toAdd    Task object being added
     * @param numTasks number of tasks in TaskList
     */
    public String getNewTaskMessage(Task toAdd, int numTasks) {
        StringBuilder response = new StringBuilder();
        response.append("The following task has been added:\n");
        response.append("    ").append(toAdd.toString()).append("\n");
        response.append("There are now ").append(numTasks).append(" tasks in the list");
        return response.toString();
    }

    /**
     * Returns error message for command if a non-number was inputted
     *
     * @param command command which failed (delete, mark, unmark)
     */
    public String getNotNumberMessage(String command) {
        StringBuilder response = new StringBuilder();
        response.append("Not a number.\n");
        response.append("Usage: ").append(command).append(" <task number>");
        return response.toString();
    }

    /**
     * Returns error message for command if index was out of range
     * (delete, mark, unmark)
     */
    public String getOutOfRangeMessage() {
        return "Out of range.\nType a number within the range of current tasks";
    }

    /**
     * Returns message when task is marked as done
     *
     * @param tt Task object marked as done
     */
    public String getMarkedMessage(Task tt) {
        StringBuilder response = new StringBuilder();
        response.append("The following task is now marked as done:\n");
        response.append(tt.toString());
        return response.toString();
    }

    /**
     * Returns message when task is marked as undone
     *
     * @param tt Task object marked as undone
     */
    public String getUnmarkedMessage(Task tt) {
        StringBuilder response = new StringBuilder();
        response.append("The following task is now marked as undone:\n");
        response.append(tt.toString());
        return response.toString();

    }

    /**
     * Returns error message for incorrect todo command usage
     */
    public String getInvalidTodoMessage() {
        return "Missing description.\nUsage: todo <task description>";
    }

    /**
     * Returns error message for incorrect deadline command usage
     */
    public String getInvalidDeadlineMessage() {
        StringBuilder response = new StringBuilder();
        response.append("Incorrect format\n");
        response.append("Usage: deadline <task description> /by <deadline>\n");
        response.append("Time format: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format\n");
        return response.toString();
    }

    /**
     * Returns error message for incorrect event command usage
     */
    public String getInvalidEventMessage() {
        StringBuilder response = new StringBuilder();
        response.append("Incorrect format\n");
        response.append("Usage: event <task description> /from <start time> /to <end time>\n");
        response.append("Time format: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format\n");
        return response.toString();
    }

    /**
     * Returns message when task is deleted from TaskList
     *
     * @param tt       Task object removed from TaskList
     * @param numTasks number of tasks remaining in TaskList
     */
    public String getTaskDeletedMessage(Task tt, int numTasks) {
        StringBuilder response = new StringBuilder();
        response.append("The following task has been removed:\n");
        response.append(tt.toString());
        response.append("\nRemaining tasks stored: ").append(numTasks);
        return response.toString();
    }

    /**
     * Returns tasks found for the term searched for using 'find'
     * If no tasks are found, returns message indicating no match
     *
     * @param foundTasks List containing matching tasks containing the search term
     * @param term       String of search term
     */
    public String getFoundTasksMessage(List<Task> foundTasks, String term) {
        StringBuilder response = new StringBuilder();

        if (foundTasks.isEmpty()) {
            return "No matching tasks found for '" + term + "'";
        } else {
            response.append("Matching tasks found for '").append(term).append("' :\n");
            for (int i = 0; i < foundTasks.size(); i++) {
                response.append(foundTasks.get(i).toString()).append("\n");
            }
        }

        return response.toString();
    }

    /**
     * Returns message when find command is in invalid format
     */
    public String getInvalidFindMessage() {
        return "Invalid format\nUsage: find <search term>";
    }

}
