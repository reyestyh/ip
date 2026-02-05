package advisor;

import java.time.format.DateTimeParseException;
import java.util.List;

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
        this.setup();
    }

    /**
     * Updates taskList with Task object
     * Prints message for user indicating task being added
     *
     * @param toAdd Task being added
     */
    public void updateToDoList(Task toAdd) {
        this.taskList.addTask(toAdd);
    }

    /**
     * Main program of advisor chatbot
     */
    public void run() {
        this.taskList.populateList();
        this.userInterface.getStartupMessage();

        boolean isSessionFinished = false;

        while (!isSessionFinished) {
            String input = this.userInterface.readInput();
            String command = this.userInterface.readCommand(input);

            switch (command) {
            case "bye":
                boolean updateSuccess = this.taskList.updateStorage();

                this.userInterface.getUpdateFileMessage(updateSuccess);
                this.userInterface.getExitMessage();
                isSessionFinished = true;
                return;

            case "list":
                this.userInterface.getCurrentTasks(this.taskList);
                break;

            case "mark":
                int markIdx = 0;

                try {
                    markIdx = InputParser.markParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.getNotNumberMessage("mark");
                    break;
                }

                markIdx -= 1;

                try {
                    this.taskList.completeTask(markIdx);
                    Task fin = this.taskList.getTask(markIdx);
                    this.userInterface.getMarkedMessage(fin);
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.getOutOfRangeMessage();
                }
                break;

            case "unmark":
                int unmarkIdx = 0;

                try {
                    unmarkIdx = InputParser.unmarkParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.getNotNumberMessage("unmark");
                    break;
                }

                unmarkIdx -= 1;

                try {
                    this.taskList.undoTask(unmarkIdx);
                    Task undone = this.taskList.getTask(unmarkIdx);
                    this.userInterface.getUnmarkedMessage(undone);
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.getOutOfRangeMessage();
                }

                break;

            case "todo":
                String desc = "";
                try {
                    desc = InputParser.todoParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.getInvalidTodoMessage();
                    break;
                }

                updateToDoList(new ToDoTask(desc));

                break;

            case "deadline":
                String[] deadlineData = InputParser.deadlineParser(input);
                if (deadlineData == null) {
                    this.userInterface.getInvalidDeadlineMessage();
                } else {
                    try {
                        updateToDoList(new DeadlineTask(deadlineData[0], deadlineData[1]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.getInvalidDeadlineMessage();
                    }
                }
                break;

            case "event":
                String[] taskData = InputParser.eventParser(input);
                if (taskData == null) {
                    this.userInterface.getInvalidEventMessage();
                } else {
                    try {
                        updateToDoList(new EventTask(taskData[0], taskData[1], taskData[2]));
                    } catch (DateTimeParseException e) {
                        this.userInterface.getInvalidEventMessage();
                    }
                }
                break;

            case "delete":
                int deleteIdx = 0;
                try {
                    deleteIdx = InputParser.deleteParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.getNotNumberMessage("delete");
                    break;
                }
                deleteIdx -= 1;
                try {
                    Task removed = taskList.deleteTask(deleteIdx);
                    this.userInterface.getTaskDeletedMessage(removed, taskList.getNumTasks());
                } catch (IndexOutOfBoundsException e) {
                    this.userInterface.getOutOfRangeMessage();
                }

                break;

            case "find":

                String term = "";
                try {
                    term = InputParser.findParser(input);
                } catch (AdvisorException e) {
                    this.userInterface.getInvalidFindMessage();
                    break;
                }

                this.userInterface.getFoundTasksMessage(this.taskList.findTasks(term), term);
                break;
            default:
                this.userInterface.getInvalidCommandMessage();

            }
        }

    }

    public void setup() {
        this.taskList.populateList();
        this.userInterface.getStartupMessage();
    }


    public String getResponse(String userInput) {

        String command = this.userInterface.readCommand(userInput);

        switch (command) {
        case "bye":
            boolean isSuccessfulUpdate = this.taskList.updateStorage();

            return userInterface.getUpdateFileMessage(isSuccessfulUpdate) + this.userInterface.getExitMessage();


        case "list":
            return this.userInterface.getCurrentTasks(this.taskList);

        case "mark":
            int markIdx = 0;

            try {
                markIdx = InputParser.markParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getNotNumberMessage("mark");
            }

            markIdx -= 1;

            try {
                this.taskList.completeTask(markIdx);
                Task fin = this.taskList.getTask(markIdx);
                return this.userInterface.getMarkedMessage(fin);
            } catch (IndexOutOfBoundsException e) {
                return this.userInterface.getOutOfRangeMessage();
            }

        case "unmark":
            int unmarkIdx = 0;

            try {
                unmarkIdx = InputParser.unmarkParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getNotNumberMessage("unmark");

            }

            unmarkIdx -= 1;

            try {
                this.taskList.undoTask(unmarkIdx);
                Task undone = this.taskList.getTask(unmarkIdx);
                return this.userInterface.getUnmarkedMessage(undone);
            } catch (IndexOutOfBoundsException e) {
                return this.userInterface.getOutOfRangeMessage();
            }

        case "todo":
            String desc = "";
            try {
                desc = InputParser.todoParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getInvalidTodoMessage();
            }

            Task toAdd = new ToDoTask(desc);
            updateToDoList(toAdd);
            return this.userInterface.getNewTaskMessage(toAdd, this.taskList.getNumTasks());


        case "deadline":
            String[] deadlineData = InputParser.deadlineParser(userInput);
            if (deadlineData == null) {
                return this.userInterface.getInvalidDeadlineMessage();

            } else {
                try {
                    toAdd = new DeadlineTask(deadlineData[0], deadlineData[1]);
                    updateToDoList(toAdd);
                    return this.userInterface.getNewTaskMessage(toAdd, this.taskList.getNumTasks());

                } catch (DateTimeParseException e) {
                    return this.userInterface.getInvalidDeadlineMessage();
                }
            }

        case "event":
            String[] taskData = InputParser.eventParser(userInput);
            if (taskData == null) {
                return this.userInterface.getInvalidEventMessage();
            } else {
                try {
                    toAdd = new EventTask(taskData[0], taskData[1], taskData[2]);
                    updateToDoList(toAdd);
                    return this.userInterface.getNewTaskMessage(toAdd, this.taskList.getNumTasks());
                } catch (DateTimeParseException e) {
                    return this.userInterface.getInvalidEventMessage();
                }
            }

        case "delete":
            int deleteIdx = 0;
            try {
                deleteIdx = InputParser.deleteParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getNotNumberMessage("delete");
            }

            deleteIdx -= 1;

            try {
                Task removed = taskList.deleteTask(deleteIdx);
                return this.userInterface.getTaskDeletedMessage(removed, taskList.getNumTasks());
            } catch (IndexOutOfBoundsException e) {
                return this.userInterface.getOutOfRangeMessage();
            }

        case "find":

            String term = "";
            try {
                term = InputParser.findParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getInvalidFindMessage();

            }

            List<Task> matches = this.taskList.findTasks(term);

            return this.userInterface.getFoundTasksMessage(matches, term);


        default:
            return this.userInterface.getInvalidCommandMessage();
        }

    }

    public static void main(String[] args) {
        new Advisor().run();
    }


}
