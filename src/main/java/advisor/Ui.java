package advisor;

import java.util.List;
import java.util.Scanner;

public class Ui {

    private final String logo = "            _       _                \n" +
            "           | |     (_)               \n" +
            "   __ _  __| |_   ___ ___  ___  _ __ \n" +
            "  / _` |/ _` \\ \\ / / / __|/ _ \\| '__|\n" +
            " | (_| | (_| |\\ V /| \\__ \\ (_) | |   \n" +
            "  \\__,_|\\__,_| \\_/ |_|___/\\___/|_|   \n" +
            "                                     \n" +
            "                                     ";
    private final String name = "Advisor";
    private final String line = "____________________________________________________________";
    private Scanner input;

    public Ui() {
        this.input = new Scanner(System.in);
    }

    public String readCommand(String input) {
        return input.split(" ")[0];
    }

    /**
     * Prints startup logo and welcome message
     */
    public void showStart() {
        System.out.println(logo);
        System.out.println(line);
        System.out.println("Hello. I am " + name);
        System.out.println("What do you want me to do?");
    }

    /**
     * Prints exit message
     */
    public void showExit() {
        System.out.println(line);
        System.out.println("End of Session. Goodbye.");
        System.out.println(line);
    }

    /**
     * Prints prompt for user input, and returns a stripped input from user
     * @return String (stripped) of user input to interface
     */
    public String readInput() {
        System.out.println("Enter a command:");
        return input.nextLine().strip();
    }

    /**
     * Prints success/failure message for updating data file AdvisorTaskData.txt
     * @param successfulUpdate boolean of whether file was updated successfully
     */
    public void showUpdateFile(boolean successfulUpdate) {
        System.out.println(line);
        if (successfulUpdate) {
            System.out.println("Data file successfully updated.");
        } else {
            System.out.println("An error occurred while updating the data file.");
        }
    }

    /**
     * Prints current tasks in TaskList
     * @param taskList TaskList object storing tasks in current session
     */
    public void showTasks(TaskList taskList) {
        System.out.println(line);
        System.out.println(taskList.getTasksString());
        System.out.println(line);
    }

    /**
     * Prints error message for invalid command
     */
    public void showInvalidCommand() {
        System.out.println(line);
        System.out.println("Invalid command. Try again.");
        System.out.println(line);
    }

    /**
     * Prints new task added from todo, deadline, or event commands
     * @param toAdd Task object being added
     * @param numTasks number of tasks in TaskList
     */
    public void showNewTask(Task toAdd, int numTasks) {
        System.out.println(line);
        System.out.println(line);
        System.out.println("The following task has been added:");
        System.out.println("    " + toAdd.toString());
        System.out.println("There are now " + numTasks + " tasks in the list");
        System.out.println(line);
    }

    /**
     * Prints error message for command if a non-number was inputted
     * @param command command which failed (delete, mark, unmark)
     */
    public void showNotNumber(String command) {
        System.out.println(line);
        System.out.println("Not a number.");
        System.out.println("Usage: " + command + " <task number>");
        System.out.println(line);
    }

    /**
     * Prints error message for command if index was out of range
     * (delete, mark, unmark)
     */
    public void showOutOfRange() {
        System.out.println(line);
        System.out.println("Out of range.\nType a number within the range of current tasks");
        System.out.println(line);
    }

    /**
     * Prints message when task is marked as done
     * @param tt Task object marked as done
     */
    public void showMarked(Task tt) {
        System.out.println(line);
        System.out.println("The following task is now marked as done:");
        System.out.println(tt.toString());
        System.out.println(line);
    }

    /**
     * Prints message when task is marked as undone
     * @param tt Task object marked as undone
     */
    public void showUnmarked(Task tt) {
        System.out.println(line);
        System.out.println("The following task is now marked as undone:");
        System.out.println(tt.toString());
        System.out.println(line);
    }

    /**
     * Prints error message for incorrect todo command usage
     */
    public void showInvalidTodo() {
        System.out.println(line);
        System.out.println("Missing description.");
        System.out.println("Usage: todo <task description>");
        System.out.println(line);
    }

    /**
     * Prints error message for incorrect deadline command usage
     */
    public void showInvalidDeadline() {
        System.out.println(line);
        System.out.println("Incorrect format");
        System.out.println("Usage: deadline <task description> /by <deadline>");
        System.out.println("Time format: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format");
        System.out.println(line);
    }

    /**
     * Prints error message for incorrect event command usage
     */
    public void showInvalidEvent() {
        System.out.println(line);
        System.out.println("Incorrect format");
        System.out.println("Usage: event <task description> /from <start time> /to <end time>");
        System.out.println("Time format: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format");
        System.out.println(line);
    }

    /**
     * Prints message when task is deleted from TaskList
     * @param tt Task object removed from TaskList
     * @param numTasks number of tasks remaining in TaskList
     */
    public void showTaskDeleted(Task tt, int numTasks) {
        System.out.println(line);
        System.out.println("The following task has been removed:");
        System.out.println(tt.toString());
        System.out.println("Remaining tasks stored: " + numTasks);
        System.out.println(line);
    }

    /**
     * Prints tasks found for the term searched for using 'find'
     * If no tasks are found, print message indicating no match
     *
     * @param foundTasks List containing matching tasks containing the search term
     * @param term String of search term
     */
    public void showFoundTasks(List<Task> foundTasks, String term) {
        System.out.println(line);

        if (foundTasks.isEmpty()) {
            System.out.println("No matching tasks found for '" + term + "'");
        } else {
            System.out.println("Matching tasks found for '" + term + "':");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println(foundTasks.get(i).toString());
            }
        }

        System.out.println(line);
    }

    /**
     * Prints message when find command is in invalid format
     */
    public void showInvalidFind() {
        System.out.println(line);
        System.out.println("Invalid format");
        System.out.println("Usage: find <search term>");
        System.out.println(line);
    }

}
