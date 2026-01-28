import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Advisor {

    private Storage storage;
    private TaskList taskList;
    private Ui userInterface;

    public Advisor() {
        this.userInterface = new Ui();
        this.storage = new Storage();
        this.taskList = new TaskList(this.storage);
    }

    public void updateToDoList(Task toAdd) {
        this.taskList.addTask(toAdd);
        this.userInterface.showNewTask(toAdd, this.taskList.getNumTasks());
    }

    public void run() {
        this.taskList.populateList();
        this.userInterface.showStart();

        boolean endSession = false;

        while (!endSession) {
            String input = this.userInterface.readInput();
            String command = this.userInterface.readCommand(input);

            if (input.equals("bye")) {

                boolean updateSuccess = this.taskList.updateStorage();

                this.userInterface.showUpdateFile(updateSuccess);
                this.userInterface.showExit();
                endSession = true;
                return;

            } else if (command.equals("list")) {

                this.userInterface.showTasks(this.taskList);

            } else if (command.equals("mark")) {

                int idx = InputParser.markParser(input);

                if (idx == -1) {
                    this.userInterface.showNotNumber("mark");

                } else {
                    idx -= 1;

                    try {
                        this.taskList.completeTask(idx);
                        Task fin = this.taskList.getTask(idx);
                        this.userInterface.showMarked(fin);
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }

                }

            } else if (command.equals("unmark")) {
                int idx = InputParser.unmarkParser(input);

                if (idx == -1) {
                    this.userInterface.showNotNumber("unmark");

                } else {
                    idx -= 1;

                    try {
                        this.taskList.undoTask(idx);
                        Task undone = this.taskList.getTask(idx);
                        this.userInterface.showUnmarked(undone);
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }
                }

            } else if (command.equals("todo")) {
                String desc = InputParser.todoParser(input);
                if (desc.isEmpty()) {
                    this.userInterface.showInvalidTodo();
                } else {
                    updateToDoList(new ToDoTask(desc));
                }


            } else if (command.equals("deadline")) {
                String[] dd = InputParser.deadlineParser(input);
                if (dd == null) {
                    this.userInterface.showInvalidDeadline();
                } else {
                    try {
                        updateToDoList(new DeadlineTask(dd[0], dd[1]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.showInvalidDeadline();
                    }
                }

            } else if (command.equals("event")) {
                String[] dd = InputParser.eventParser(input);
                if (dd == null) {
                    this.userInterface.showInvalidEvent();
                } else {
                    try {
                        updateToDoList(new EventTask(dd[0], dd[1], dd[2]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.showInvalidEvent();
                    }
                }


            } else if (command.equals("delete")) {

                int idx = InputParser.deleteParser(input);
                if (idx == -1) {
                    this.userInterface.showNotNumber("delete");
                } else {
                    idx -= 1;
                    try {
                        Task removed = taskList.deleteTask(idx);
                        this.userInterface.showTaskDeleted(removed, taskList.getNumTasks());
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }
                }

            } else {
                this.userInterface.showInvalidCommand();
            }
        }
    }

        }


}
