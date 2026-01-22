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
        System.out.println("    " + toAdd.toString());
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
            String[] inputStrs = input.split(" ");
            String command = inputStrs[0].toLowerCase();

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

            } else if (command.equals("mark")) {

                int idx = InputParser.markParser(input);

                if (idx == -1) {
                    System.out.println(line);
                    System.out.println("Not a number.");
                    System.out.println("Usage: mark <task number>");
                    System.out.println(line);

                } else {
                    idx -= 1;
                    String feedback = "";

                    try {
                        Task toUpdate = taskList.get(idx);
                        toUpdate.finishTask();
                        feedback = "The following task is now marked as done:\n" + toUpdate;
                    } catch (IndexOutOfBoundsException e) {
                        feedback = "Out of range. \nType a number within the range of current tasks";
                    } finally {
                        System.out.println(line);
                        System.out.println(feedback);
                        System.out.println(line);
                    }

                }

            } else if (command.equals("unmark")) {
                int idx = InputParser.unmarkParser(input);

                if (idx == -1) {
                    System.out.println(line);
                    System.out.println("Not a number.");
                    System.out.println("Usage: mark <task number>");
                    System.out.println(line);

                } else {
                    idx -= 1;
                    String feedback = "";

                    try {
                        Task toUpdate = taskList.get(idx);
                        toUpdate.undo();
                        feedback = "The following task is now marked as undone:\n" + toUpdate;
                    } catch (IndexOutOfBoundsException e) {
                        feedback = "Out of range. \nType a number within the range of current tasks";
                    } finally {
                        System.out.println(line);
                        System.out.println(feedback);
                        System.out.println(line);
                    }

                }

            } else if (command.equals("todo")) {
                String desc = InputParser.todoParser(input);
                if (desc.isEmpty()) {
                    System.out.println(line);
                    System.out.println("Missing description.");
                    System.out.println("Usage: todo <task description>");
                    System.out.println(line);
                } else {
                    updateToDoList(new ToDoTask(desc));
                }


            } else if (command.equals("deadline")) {
                String[] dd = InputParser.deadlineParser(input);
                if (dd == null) {
                    System.out.println(line);
                    System.out.println("Incorrect format");
                    System.out.println("Usage: deadline <task description> /by <deadline>");
                    System.out.println(line);
                } else {
                    updateToDoList(new DeadlineTask(dd[0], dd[1]));
                }

            } else if (command.equals("event")) {
                String[] dd = InputParser.eventParser(input);
                if (dd == null) {
                    System.out.println(line);
                    System.out.println("Incorrect format");
                    System.out.println("Usage: event <task description> /from <start time> /to <end time>");
                    System.out.println(line);
                } else {
                    updateToDoList(new EventTask(dd[0], dd[1], dd[2]));
                }


            } else if (command.equals("delete")) {

                int idx = InputParser.deleteParser(input);

                if (idx == -1) {
                    System.out.println(line);
                    System.out.println("Not a number.");
                    System.out.println("Usage: mark <task number>");
                    System.out.println(line);

                } else {
                    idx -= 1;
                    String feedback = "";

                    try {
                        Task removed = taskList.remove(idx);
                        feedback = "The following task has been removed:\n" + removed.toString() +
                                "\nRemaining tasks stored: " + taskList.size();
                    } catch (IndexOutOfBoundsException e) {
                        feedback = "Out of range. \nType a number within the range of current tasks";
                    } finally {
                        System.out.println(line);
                        System.out.println(feedback);
                        System.out.println(line);
                    }

                }


            } else {
                System.out.println(line);
                System.out.println("Invalid command. Try again.");
                System.out.println(line);
            }

        }


    }
}
