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
        int descriptionIndex = 0;

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

            // Converts index input from user to 0-based for code
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

            // Converts index input from user to 0-based for code
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
            assert(!desc.isEmpty());

            Task todoToAdd = new ToDoTask(desc);
            updateToDoList(todoToAdd);
            return this.userInterface.getNewTaskMessage(todoToAdd, this.taskList.getNumTasks());


        case "deadline":
            int deadlineIndex = 1;

            String[] deadlineData = InputParser.deadlineParser(userInput);
            if (deadlineData == null) {
                return this.userInterface.getInvalidDeadlineMessage();
            }

            Task deadlineToAdd = new DeadlineTask(deadlineData[descriptionIndex], deadlineData[deadlineIndex]);
            try {
                updateToDoList(deadlineToAdd);
                return this.userInterface.getNewTaskMessage(deadlineToAdd, this.taskList.getNumTasks());

            } catch (DateTimeParseException e) {
                return this.userInterface.getInvalidDeadlineMessage();
            }


        case "event":
            int startTimeIndex = 1;
            int endTimeIndex = 2;

            String[] taskData = InputParser.eventParser(userInput);
            if (taskData == null) {
                return this.userInterface.getInvalidEventMessage();
            }

            Task eventToAdd = new EventTask(taskData[descriptionIndex],
                                            taskData[startTimeIndex],
                                            taskData[endTimeIndex]);
            try {
                updateToDoList(eventToAdd);
                return this.userInterface.getNewTaskMessage(eventToAdd, this.taskList.getNumTasks());
            } catch (DateTimeParseException e) {
                return this.userInterface.getInvalidEventMessage();
            }


        case "delete":
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

        case "find":

            String term = "";
            try {
                term = InputParser.findParser(userInput);
            } catch (AdvisorException e) {
                return this.userInterface.getInvalidFindMessage();
            }
            assert(!term.isEmpty());

            List<Task> matches = this.taskList.findTasks(term);

            return this.userInterface.getFoundTasksMessage(matches, term);


        default:
            return this.userInterface.getInvalidCommandMessage();
        }

    }


}
