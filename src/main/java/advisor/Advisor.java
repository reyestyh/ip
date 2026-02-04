package advisor;

import java.time.format.DateTimeParseException;

/**
 * Runs the main Advisor chatbot to manage a user's tasks
 */
public class Advisor {

    private Storage storage;
    private TaskList taskList;
    private Ui userInterface;

    /**
     * Constructs an Advisor instance with its Ui, storage, and taskList
     */
    public Advisor() {
        this.userInterface = new Ui();
        this.storage = new Storage();
        this.taskList = new TaskList(this.storage);
    }

    /**
     * Updates taskList with Task object
     * Prints message for user indicating task being added
     *
     * @param toAdd Task being added
     */
    public void updateToDoList(Task toAdd) {
        this.taskList.addTask(toAdd);
        this.userInterface.showNewTask(toAdd, this.taskList.getNumTasks());
    }

    /**
     * Main program of advisor chatbot
     */
    public void run() {
        this.taskList.populateList();
        this.userInterface.showStart();

        boolean isSessionFinished = false;

        while (!isSessionFinished) {
            String input = this.userInterface.readInput();
            String command = this.userInterface.readCommand(input);

            switch (command) {
            case "bye":
                boolean updateSuccess = this.taskList.updateStorage();

                this.userInterface.showUpdateFile(updateSuccess);
                this.userInterface.showExit();
                isSessionFinished = true;
                return;

            case "list":
                this.userInterface.showTasks(this.taskList);
                break;

            case "mark":
                int markIdx = 0;

                try {
                    markIdx = InputParser.markParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.showNotNumber("mark");
                    break;
                }

                markIdx -= 1;

                try {
                    this.taskList.completeTask(markIdx);
                    Task fin = this.taskList.getTask(markIdx);
                    this.userInterface.showMarked(fin);
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.showOutOfRange();
                }
                break;

            case "unmark":
                int unmarkIdx = 0;

                try {
                    unmarkIdx = InputParser.unmarkParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.showNotNumber("unmark");
                    break;
                }

                unmarkIdx -= 1;

                try {
                    this.taskList.undoTask(unmarkIdx);
                    Task undone = this.taskList.getTask(unmarkIdx);
                    this.userInterface.showUnmarked(undone);
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.showOutOfRange();
                }

                break;

            case "todo":
                String desc = "";
                try {
                    desc = InputParser.todoParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.showInvalidTodo();
                    break;
                }

                updateToDoList(new ToDoTask(desc));

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
                int deleteIdx = 0;
                try {
                    deleteIdx = InputParser.deleteParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.showNotNumber("delete");
                    break;
                }
                deleteIdx -= 1;
                try {
                    Task removed = taskList.deleteTask(deleteIdx);
                    this.userInterface.showTaskDeleted(removed, taskList.getNumTasks());
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.showOutOfRange();
                }

                break;

            case "find":

                String term = "";
                try {
                    term = InputParser.findParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.showInvalidFind();
                    break;
                }

                this.userInterface.showFoundTasks(this.taskList.findTasks(term), term);
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
