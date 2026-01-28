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

    public void showStart() {
        System.out.println(logo);
        System.out.println(line);
        System.out.println("Hello. I am " + name);
        System.out.println("What do you want me to do?");
    }

    public void showExit() {
        System.out.println(line);
        System.out.println("End of Session. Goodbye.");
        System.out.println(line);
    }

    public String readInput() {
        System.out.println("Enter a command:");
        return input.nextLine().strip();
    }

    public void showUpdateFile(boolean successfulUpdate) {
        System.out.println(line);
        if (successfulUpdate) {
            System.out.println("Data file successfully updated.");
        } else {
            System.out.println("An error occurred while updating the data file.");
        }
    }

    public void showTasks(TaskList taskList) {
        System.out.println(line);
        System.out.println(taskList.getTasksString());
        System.out.println(line);
    }

    public void showInvalidCommand() {
        System.out.println(line);
        System.out.println("Invalid command. Try again.");
        System.out.println(line);
    }

    public void showNewTask(Task toAdd, int numTasks) {
        System.out.println(line);
        System.out.println(line);
        System.out.println("The following task has been added:");
        System.out.println("    " + toAdd.toString());
        System.out.println("There are now " + numTasks + " tasks in the list");
        System.out.println(line);
    }

    public void showMarkNotNumber(String mark) {
        System.out.println(line);
        System.out.println("Not a number.");
        System.out.println("Usage: " + mark + " <task number>");
        System.out.println(line);
    }

    public void showOutOfRange() {
        System.out.println(line);
        System.out.println("Out of range.\nType a number within the range of current tasks\"");
        System.out.println(line);
    }

    public void showMarked(Task tt) {
        System.out.println(line);
        System.out.println("The following task is now marked as done:");
        System.out.println(tt.toString());
        System.out.println(line);
    }

    public void showUnmarked(Task tt) {
        System.out.println(line);
        System.out.println("The following task is now marked as undone:");
        System.out.println(tt.toString());
        System.out.println(line);
    }

    public void showInvalidTodo() {
        System.out.println(line);
        System.out.println("Missing description.");
        System.out.println("Usage: todo <task description>");
        System.out.println(line);
    }

    public void showInvalidDeadline() {
        System.out.println(line);
        System.out.println("Incorrect format");
        System.out.println("Usage: deadline <task description> /by <deadline>");
        System.out.println("Deadline: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format");
        System.out.println(line);
    }


}
