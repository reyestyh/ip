import java.util.ArrayList;
import java.util.Scanner;

public class Advisor {

    static String logo = "            _       _                \n" +
            "           | |     (_)               \n" +
            "   __ _  __| |_   ___ ___  ___  _ __ \n" +
            "  / _` |/ _` \\ \\ / / / __|/ _ \\| '__|\n" +
            " | (_| | (_| |\\ V /| \\__ \\ (_) | |   \n" +
            "  \\__,_|\\__,_| \\_/ |_|___/\\___/|_|   \n" +
            "                                     \n" +
            "                                     ";
    static String name = "Advisor";
    static String line = "____________________________________________________________";

    static Scanner input = new Scanner(System.in);
    static ArrayList<Task> taskList = new ArrayList<>();

    public static String getInput() {
        return input.nextLine().strip();
    }

    public static void updateToDoList(Task toAdd) {
        taskList.add(toAdd);
        System.out.println(line);
        System.out.println("The following task has been added:");
        System.out.println(toAdd.toString());
        System.out.println("There are now " + taskList.size() + " tasks in the list");
        System.out.println(line);
    }

    public static void main(String[] args) {

        System.out.println(logo);
        System.out.println(line);
        System.out.println("Hello. I am " + name);
        System.out.println("What do you want me to do?");

        boolean endSession = false;

        while (!endSession) {
            System.out.println("Enter a command:");
            String input = getInput();
            String command = input.split(" ")[0].toLowerCase();

            if (input.equals("bye")) {

                System.out.println(line);
                System.out.println("End of Session. Goodbye.");
                System.out.println(line);
                endSession = true;
                return;

            } else if (command.equals("list")) {

                System.out.println(line);
                System.out.println("Current Tasks:");
                for (int i = 0; i < taskList.size(); i += 1) {
                    Task currTask = taskList.get(i);
                    System.out.println((i + 1) + ". " + currTask.toString());
                }
                System.out.println(line);

            } else if (command.startsWith("mark")) {

                System.out.println(line);
                int idx = InputParser.markParser(input) - 1;
                Task toUpdate = taskList.get(idx);
                toUpdate.finishTask();
                System.out.println("This following task is now marked as done:");
                System.out.println(toUpdate);
                System.out.println(line);

            } else if (command.startsWith("unmark")) {
                System.out.println(line);
                int idx = InputParser.unmarkParser(input) - 1;
                Task toUpdate = taskList.get(idx);
                toUpdate.undo();
                System.out.println("This following task is now marked as undone:");
                System.out.println(toUpdate);
                System.out.println(line);

            } else if (command.startsWith("todo")) {
                String desc = InputParser.todoParser(input);
                updateToDoList(new ToDoTask(desc));

            } else if (command.startsWith("deadline")) {
                String[] dd = InputParser.deadlineParser(input);
                updateToDoList(new DeadlineTask(dd[0], dd[1]));
            } else if (command.startsWith("event")) {
                String[] dd = InputParser.eventParser(input);
                updateToDoList(new EventTask(dd[0], dd[1], dd[2]));
            }

        }


    }
}
