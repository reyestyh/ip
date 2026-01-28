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



}
