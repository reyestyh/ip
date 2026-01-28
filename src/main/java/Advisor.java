import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Advisor {

    static String line = "____________________________________________________________";

    static Scanner input = new Scanner(System.in);
    static Storage storage = new Storage();
    static TaskList taskList = new TaskList(storage);



    public static String getInput() {
        return input.nextLine().strip();
    }

    public static void updateToDoList(Task toAdd) {
        taskList.addTask(toAdd);
        System.out.println(line);
        System.out.println("The following task has been added:");
        System.out.println("    " + toAdd.toString());
        System.out.println("There are now " + taskList.getNumTasks()+ " tasks in the list");
        System.out.println(line);
    }

    public static void main(String[] args) {

        taskList.populateList();

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

                if (taskList.updateStorage()) {
                    System.out.println("Data file successfully updated.");
                } else {
                    System.out.println("An error occurred while updating the data file.");
                }

                System.out.println(line);
                System.out.println("End of Session. Goodbye.");
                System.out.println(line);
                endSession = true;
                return;

            } else if (command.equals("list")) {

                System.out.println(line);
                System.out.println("Current Tasks:");
                System.out.println(taskList.getTasksString());
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
                        taskList.completeTask(idx);
                        Task fin = taskList.getTask(idx);
                        feedback = "The following task is now marked as done:\n" + fin.toString();
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
                        taskList.undoTask(idx);
                        Task undone = taskList.getTask(idx);
                        feedback = "The following task is now marked as undone:\n" + undone.toString();
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
                    try {
                        updateToDoList(new DeadlineTask(dd[0], dd[1]));
                    } catch (DateTimeParseException e) {
                        System.out.println("Incorrect format");
                        System.out.println("Usage: deadline <task description> /by <deadline>");
                        System.out.println("Deadline: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format");
                    }
                }

            } else if (command.equals("event")) {
                String[] dd = InputParser.eventParser(input);
                if (dd == null) {
                    System.out.println(line);
                    System.out.println("Incorrect format");
                    System.out.println("Usage: event <task description> /from <start time> /to <end time>");
                    System.out.println(line);
                } else {
                    try {
                        updateToDoList(new EventTask(dd[0], dd[1], dd[2]));
                    } catch (DateTimeParseException e) {
                        System.out.println("Incorrect format");
                        System.out.println("Usage: event <task description> /from <start time> /to <end time>");
                        System.out.println("Time format: 'yyyy-MM-dd HHmm' , HHmm is the time in 24H format");
                    }
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
                        Task removed = taskList.deleteTask(idx);
                        feedback = "The following task has been removed:\n" + removed.toString() +
                                "\nRemaining tasks stored: " + taskList.getNumTasks();
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
