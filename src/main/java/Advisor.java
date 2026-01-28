import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Advisor {

    static String line = "____________________________________________________________";

    static Scanner input = new Scanner(System.in);
    static Storage storage = new Storage();
    static TaskList taskList = new TaskList(storage);
    static Ui userInterface = new Ui();


    public static String getInput() {
        return input.nextLine().strip();
    }

    public static void updateToDoList(Task toAdd) {
        taskList.addTask(toAdd);
        userInterface.showNewTask(toAdd, taskList.getNumTasks());
    }

    public static void main(String[] args) {

        taskList.populateList();

        userInterface.showStart();

        boolean endSession = false;

        while (!endSession) {
            String input = userInterface.readInput();
            String[] inputStrs = input.split(" ");
            String command = inputStrs[0].toLowerCase();

            if (input.equals("bye")) {

                boolean updateSuccess = taskList.updateStorage();

                userInterface.showUpdateFile(updateSuccess);
                userInterface.showExit();
                endSession = true;
                return;

            } else if (command.equals("list")) {

                userInterface.showTasks(taskList);

            } else if (command.equals("mark")) {

                int idx = InputParser.markParser(input);

                if (idx == -1) {
                    userInterface.showMarkNotNumber("mark");

                } else {
                    idx -= 1;

                    try {
                        taskList.completeTask(idx);
                        Task fin = taskList.getTask(idx);
                        userInterface.showMarked(fin);
                    } catch (IndexOutOfBoundsException e) {
                        userInterface.showOutOfRange();
                    }

                }

            } else if (command.equals("unmark")) {
                int idx = InputParser.unmarkParser(input);

                if (idx == -1) {
                    userInterface.showMarkNotNumber("unmark");

                } else {
                    idx -= 1;

                    try {
                        taskList.undoTask(idx);
                        Task undone = taskList.getTask(idx);
                        userInterface.showUnmarked(undone);
                    } catch (IndexOutOfBoundsException e) {
                        userInterface.showOutOfRange();
                    }
                }

            } else if (command.equals("todo")) {
                String desc = InputParser.todoParser(input);
                if (desc.isEmpty()) {
                    userInterface.showInvalidTodo();
                } else {
                    updateToDoList(new ToDoTask(desc));
                }


            } else if (command.equals("deadline")) {
                String[] dd = InputParser.deadlineParser(input);
                if (dd == null) {
                    userInterface.showInvalidDeadline();
                } else {
                    try {
                        updateToDoList(new DeadlineTask(dd[0], dd[1]));
                    } catch (DateTimeParseException e) {
                        userInterface.showInvalidDeadline();
                    }
                }

            } else if (command.equals("event")) {
                String[] dd = InputParser.eventParser(input);
                if (dd == null) {
                    userInterface.showInvalidEvent();
                } else {
                    try {
                        updateToDoList(new EventTask(dd[0], dd[1], dd[2]));
                    } catch (DateTimeParseException e) {
                        userInterface.showInvalidEvent();
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
                userInterface.showInvalidCommand();
            }

        }


    }
}
