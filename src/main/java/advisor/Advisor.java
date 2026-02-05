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

    public void setup() {
        this.taskList.populateList();
        this.userInterface.showStart();
    }


    public String getResponse(String userInput) {

        boolean isSessionFinished = false;

        StringBuilder advisorResponse = new StringBuilder();

        String command = this.userInterface.readCommand(userInput);

        switch (command) {
        case "bye":
            boolean isSuccessfulUpdate = this.taskList.updateStorage();

            String successMessage = isSuccessfulUpdate ? "Data file successfully updated."
                    : "An error occurred while updating the data file";

            advisorResponse.append(successMessage);
            advisorResponse.append("\n");
            advisorResponse.append("Goodbye!");
            return advisorResponse.toString();


        case "list":
            advisorResponse.append("Current tasks:\n");
            advisorResponse.append(this.taskList.getTasksString());
            return advisorResponse.toString();


        case "mark":
            int markIdx = 0;

            try {
                markIdx = InputParser.markParser(userInput);
            } catch (AdvisorException e) {
                advisorResponse.append("Not a number.\n");
                advisorResponse.append("Usage: ").append(command).append(" <task number>");
                return advisorResponse.toString();

            }

            markIdx -= 1;

            try {
                this.taskList.completeTask(markIdx);
                Task fin = this.taskList.getTask(markIdx);
                advisorResponse.append("The following task is now marked as done:\n").append(fin);
            } catch (IndexOutOfBoundsException e) {
                advisorResponse.append("Out of range.\nType a number within the range of current tasks");
            }
            return advisorResponse.toString();


        case "unmark":
            int unmarkIdx = 0;

            try {
                unmarkIdx = InputParser.unmarkParser(userInput);
            } catch (AdvisorException e) {
                advisorResponse.append("Not a number.\n");
                advisorResponse.append("Usage: ").append(command).append(" <task number>");
                return advisorResponse.toString();

            }

            unmarkIdx -= 1;

            try {
                this.taskList.undoTask(unmarkIdx);
                Task undone = this.taskList.getTask(unmarkIdx);
                advisorResponse.append("The following task is now marked as undone:\n").append(undone.toString());
            } catch (IndexOutOfBoundsException e) {
                advisorResponse.append("Out of range.\nType a number within the range of current tasks");
            }

            return advisorResponse.toString();


        case "todo":
            String desc = "";
            try {
                desc = InputParser.todoParser(userInput);
            } catch (AdvisorException e) {
//                this.userInterface.showInvalidTodo();
                advisorResponse.append("Missing description.\nUsage: todo <task description>");
                return advisorResponse.toString();
            }

            Task toAdd = new ToDoTask(desc);
            updateToDoList(toAdd);
            advisorResponse.append("The following task has been added:\n    ").append(toAdd).append("\nThere are now ").append(this.taskList.getNumTasks()).append(" tasks in the list.");

            return advisorResponse.toString();

        case "deadline":
            String[] deadlineData = InputParser.deadlineParser(userInput);
            if (deadlineData == null) {
                advisorResponse.append("Invalid format.");
            } else {
                try {
                    toAdd = new DeadlineTask(deadlineData[0], deadlineData[1]);
                    updateToDoList(toAdd);
                    advisorResponse.append("The following task has been added:\n    ").append(toAdd).append("\nThere are now ").append(this.taskList.getNumTasks()).append(" tasks in the list.");
                } catch (DateTimeParseException e) {
                    advisorResponse.append("Invalid format.");
                }
            }
            return advisorResponse.toString();


        case "event":
            String[] taskData = InputParser.eventParser(userInput);
            if (taskData == null) {
                advisorResponse.append("Invalid format.");
            } else {
                try {
                    toAdd = new EventTask(taskData[0], taskData[1], taskData[2]);
                    updateToDoList(toAdd);
                    advisorResponse.append("The following task has been added:\n    ").append(toAdd).append("\nThere are now ").append(this.taskList.getNumTasks()).append(" tasks in the list.");
                } catch (DateTimeParseException e) {
                    advisorResponse.append("Invalid format.");
                }
            }
            return advisorResponse.toString();


        case "delete":
            int deleteIdx = 0;
            try {
                deleteIdx = InputParser.deleteParser(userInput);
            } catch (AdvisorException e) {
                advisorResponse.append("Not a number.\n");
                advisorResponse.append("Usage: ").append(command).append(" <task number>");
                return advisorResponse.toString();

            }
            deleteIdx -= 1;
            try {
                Task removed = taskList.deleteTask(deleteIdx);
                advisorResponse.append("The following task has been removed:\n")
                        .append(removed).append("\nRemaining tasks stored: ")
                        .append(this.taskList.getNumTasks());

            } catch (IndexOutOfBoundsException e) {
                advisorResponse.append("Out of range.\nType a number within the range of current tasks");
            }
            return advisorResponse.toString();


        case "find":

            String term = "";
            try {
                term = InputParser.findParser(userInput);
            } catch (AdvisorException e) {
                advisorResponse.append("Invalid format.\nUsage: find <search term>");
                return advisorResponse.toString();

            }

            List<Task> matches = this.taskList.findTasks(term);
            if (matches.isEmpty()) {
                advisorResponse.append("No matching tasks found for '").append(term).append("'");
            } else {
                advisorResponse.append("Matching tasks found for '").append(term).append("':\n");
                for (int i = 0; i < matches.size(); i++) {
                    advisorResponse.append(matches.get(i).toString());
                }
            }
            return advisorResponse.toString();


        default:
            advisorResponse.append("Invalid command. Try again");
            return advisorResponse.toString();

        }

    }

    public static void main(String[] args) {
        new Advisor().run();
    }


}
