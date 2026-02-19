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
     * Updates taskList with data from data file. Returns startup message
     *
     * @return String the startup message
     */
    public String setup() {
        this.taskList.populateList();
        return this.userInterface.getStartupMessage();
    }


    /**
     * Takes in user input and executes command from user.
     * Returns String response from Advisor
     *
     * @param userInput String input from user
     * @return String response from Advisor
     */
    public String getResponse(String userInput) {

        String command = this.userInterface.readCommand(userInput);
        assert (command != null); // ChatGPT recommended to add assertion here

        switch (command) {
        case "bye":
            return execByeCommand();

        case "list":
            return execListCommand();

        case "sort":
            return execSortCommand();

        case "mark":
            return execMarkCommand(userInput);

        case "unmark":
            return execUnmarkCommand(userInput);

        case "todo":
            return execTodoCommand(userInput);

        case "deadline":
            return execDeadlineCommand(userInput);

        case "event":
            return execEventCommand(userInput);

        case "delete":
            return execDeleteCommand(userInput);

        case "find":
            return execFindCommand(userInput);

        default:
            return this.userInterface.getInvalidCommandMessage();
        }

    }

    private String execListCommand() {
        return this.userInterface.getCurrentTasks(this.taskList);
    }


    private String execByeCommand() {
        boolean isSuccessfulUpdate = this.taskList.updateStorage();
        return userInterface.getUpdateFileMessage(isSuccessfulUpdate) + this.userInterface.getExitMessage();
    }


    private String execTodoCommand(String userInput) {
        String desc = "";
        try {
            desc = InputParser.todoParser(userInput).strip();
        } catch (AdvisorException e) {
            return this.userInterface.getInvalidTodoMessage();
        }

        if (desc.isEmpty()) {
            return this.userInterface.getInvalidTodoMessage();
        }

        Task todoToAdd = new ToDoTask(desc);
        updateToDoList(todoToAdd);
        return this.userInterface.getNewTaskMessage(todoToAdd, this.taskList.getNumTasks());
    }


    private String execDeadlineCommand(String userInput, int descriptionIndex) {
    private String execDeadlineCommand(String userInput) {
        int descriptionIndex = 0;
        int deadlineIndex = 1;
        int correctInfoArraySize = 2;

        String[] deadlineData = null;
        try {
            deadlineData = InputParser.deadlineParser(userInput);
        } catch (AdvisorException e) {
            return this.userInterface.getInvalidDeadlineMessage();
        }

        // ChatGPT recommended to add assertion here
        assert deadlineData.length == correctInfoArraySize;

        Task deadlineToAdd = null;

        try {
            deadlineToAdd = new DeadlineTask(deadlineData[descriptionIndex], deadlineData[deadlineIndex]);
        } catch (DateTimeParseException e) {
            return this.userInterface.getInvalidDeadlineMessage();
        }

        try {
            updateToDoList(deadlineToAdd);
            return this.userInterface.getNewTaskMessage(deadlineToAdd, this.taskList.getNumTasks());

        } catch (DateTimeParseException e) {
            return this.userInterface.getInvalidDeadlineMessage();
        }
    }


    private String execEventCommand(String userInput, int descriptionIndex) {
    private String execEventCommand(String userInput) {
        int descriptionIndex = 0;
        int startTimeIndex = 1;
        int endTimeIndex = 2;
        int correctInfoArraySize = 3;

        String[] taskData = null;
        try {
            taskData = InputParser.eventParser(userInput);
        } catch (AdvisorException e) {
            System.out.println(e.getMessage());
            return this.userInterface.getInvalidEventMessage();
        }

        // ChatGPT recommended to add assertion here
        assert taskData.length == correctInfoArraySize;

        Task eventToAdd = null;

        try {
            eventToAdd = new EventTask(taskData[descriptionIndex],
                                       taskData[startTimeIndex],
                                       taskData[endTimeIndex]);
        } catch (DateTimeParseException e) {
            return this.userInterface.getInvalidEventMessage();
        }


        try {
            updateToDoList(eventToAdd);
            return this.userInterface.getNewTaskMessage(eventToAdd, this.taskList.getNumTasks());
        } catch (DateTimeParseException e) {
            return this.userInterface.getInvalidEventMessage();
        }
    }


    private String execMarkCommand(String userInput) {
        int markIdx = 0;

        try {
            markIdx = InputParser.markParser(userInput);
        } catch (AdvisorException e) {
            return this.userInterface.getNotNumberMessage("mark");
        }

        // Converts index input from user to 0-based for code
        markIdx -= 1;

        try {
            this.taskList.completeTask(markIdx);
            Task fin = this.taskList.getTask(markIdx);
            return this.userInterface.getMarkedMessage(fin);
        } catch (IndexOutOfBoundsException e) {
            return this.userInterface.getOutOfRangeMessage();
        }
    }


    private String execUnmarkCommand(String userInput) {
        int unmarkIdx = 0;

        try {
            unmarkIdx = InputParser.unmarkParser(userInput);
        } catch (AdvisorException e) {
            return this.userInterface.getNotNumberMessage("unmark");

        }

        // Converts index input from user to 0-based for code
        unmarkIdx -= 1;

        try {
            this.taskList.undoTask(unmarkIdx);
            Task undone = this.taskList.getTask(unmarkIdx);
            return this.userInterface.getUnmarkedMessage(undone);
        } catch (IndexOutOfBoundsException e) {
            return this.userInterface.getOutOfRangeMessage();
        }
    }


    private String execDeleteCommand(String userInput) {
        int deleteIdx = 0;
        try {
            deleteIdx = InputParser.deleteParser(userInput);
        } catch (AdvisorException e) {
            return this.userInterface.getNotNumberMessage("delete");
        }

        // Converts index input from user to 0-based for code
        deleteIdx -= 1;

        try {
            Task removed = taskList.deleteTask(deleteIdx);
            return this.userInterface.getTaskDeletedMessage(removed, taskList.getNumTasks());
        } catch (IndexOutOfBoundsException e) {
            return this.userInterface.getOutOfRangeMessage();
        }
    }


    private String execFindCommand(String userInput) {
        String term = "";
        try {
            term = InputParser.findParser(userInput.strip());
        } catch (AdvisorException e) {
            return this.userInterface.getInvalidFindMessage();
        }

        List<Task> matches = this.taskList.findTasks(term);

        return this.userInterface.getFoundTasksMessage(matches, term);
    }


    private String execSortCommand() {
        if (this.taskList.getNumTasks() == 0) {
            return this.userInterface.getEmptyListNoSortMessage();
        }
        this.taskList.sortTasks();
        return this.userInterface.getSortedTasks(this.taskList);
    }

}
