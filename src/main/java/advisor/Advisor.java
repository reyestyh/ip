package advisor;

import java.time.format.DateTimeParseException;

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

            switch (command) {
            case "bye":
                boolean updateSuccess = this.taskList.updateStorage();

                this.userInterface.showUpdateFile(updateSuccess);
                this.userInterface.showExit();
                endSession = true;
                return;

            case "list":
                this.userInterface.showTasks(this.taskList);
                break;

            case "mark":
                int markIdx = InputParser.markParser(input);

                if (markIdx == -1) {
                    this.userInterface.showNotNumber("mark");

                } else {
                    markIdx -= 1;

                    try {
                        this.taskList.completeTask(markIdx);
                        Task fin = this.taskList.getTask(markIdx);
                        this.userInterface.showMarked(fin);
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }

                }
                break;

            case "unmark":
                int unamrkIdx = InputParser.unmarkParser(input);

                if (unamrkIdx == -1) {
                    this.userInterface.showNotNumber("unmark");

                } else {
                    unamrkIdx -= 1;

                    try {
                        this.taskList.undoTask(unamrkIdx);
                        Task undone = this.taskList.getTask(unamrkIdx);
                        this.userInterface.showUnmarked(undone);
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }
                }
                break;

            case "todo":
                String desc = InputParser.todoParser(input);
                if (desc.isEmpty()) {
                    this.userInterface.showInvalidTodo();
                } else {
                    updateToDoList(new ToDoTask(desc));
                }
                break;

            case "deadline":
                String[] deadlineData = InputParser.deadlineParser(input);
                if (deadlineData == null) {
                    this.userInterface.showInvalidDeadline();
                } else {
                    try {
                        updateToDoList(new DeadlineTask(deadlineData[0], deadlineData[1]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.showInvalidDeadline();
                    }
                }
                break;

            case "event":
                String[] taskData = InputParser.eventParser(input);
                if (taskData == null) {
                    this.userInterface.showInvalidEvent();
                } else {
                    try {
                        updateToDoList(new EventTask(taskData[0], taskData[1], taskData[2]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.showInvalidEvent();
                    }
                }
                break;

            case "delete":
                int deleteIdx = InputParser.deleteParser(input);
                if (deleteIdx == -1) {
                    this.userInterface.showNotNumber("delete");
                } else {
                    deleteIdx -= 1;
                    try {
                        Task removed = taskList.deleteTask(deleteIdx);
                        this.userInterface.showTaskDeleted(removed, taskList.getNumTasks());
                    } catch (IndexOutOfBoundsException e) {
                        this.userInterface.showOutOfRange();
                    }
                }
                break;

            default:
                this.userInterface.showInvalidCommand();
            }
        }
    }

    public static void main(String[] args) {
        new Advisor().run();
    }


}
