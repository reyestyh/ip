package advisor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to read and store task data in the data file for Advisor
 */
public class Storage {

    private static final String SPACER = ";;;";
    private static final String DATA_FILE_NAME = "./AdvisorTaskData.txt";

    private static final String TODO_TASK_PREFIX = "T";
    private static final String DEADLINE_TASK_PREFIX = "D";
    private static final String EVENT_TASK_PREFIX = "E";

    private static final String TASK_COMPLETED_STRING = "1";
    private static final String TASK_INCOMPLETE_STRING = "0";


    /**
     * Reads from DATA_FILE_NAME and returns tasks stored.
     * If file is not found, creates a new data file
     *
     * @return an ArrayList of String arrays containing parameters for each task
     */
    public ArrayList<String[]> readDataFile() {
        ArrayList<String[]> tasksStrsList = new ArrayList<>();
        File dataFile = new File(DATA_FILE_NAME);
        try {
            Scanner reader = new Scanner(dataFile);
            while (reader.hasNextLine()) {
                String dataLine = reader.nextLine();
                String[] taskParams = getTaskData(dataLine);
                tasksStrsList.add(taskParams);

            }
            reader.close();
            return tasksStrsList;

        } catch (FileNotFoundException e) {
            createDataFile(dataFile);

        }
        return tasksStrsList;
    }

    /**
     * Creates the data file for usage of Advisor across multiple sessions
     *
     * @param dataFile File object of AdvisorTaskData.txt
     */
    private static void createDataFile(File dataFile) {
        System.out.println("Error: File " + DATA_FILE_NAME + " not found.");
        System.out.println("Creating " + DATA_FILE_NAME + " in the current directory: "
                + System.getProperty("user.dir"));

        boolean successfulFileCreation = false;
        try {
            successfulFileCreation = dataFile.createNewFile();
        } catch (IOException d) {
            System.out.println("An error occurred.");
            d.printStackTrace();
        }

        if (successfulFileCreation) {
            System.out.println(DATA_FILE_NAME + " created.");
        } else {
            System.out.println("Unsuccessful file creation.");
        }
    }

    /**
     * Splits each line of saved data into an array of data related to each task
     *
     * @param data Line containing data of a task, with ';;;' as a spacer
     * @return String array containing data (description, completion status, etc.) for each task
     */
    private static String[] getTaskData(String data) {
        if (data.isEmpty()) {
            System.out.println("Empty line. Skipping");
        }

        return data.split(SPACER);
    }

    /**
     * Updates the data file with changes in TaskList from current session
     *
     * @param tasks ArrayList of current tasks in session
     * @return boolean indicating success/failure of updating data file
     */
    public boolean updateDataFile(ArrayList<Task> tasks) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(DATA_FILE_NAME);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }


        for (int i = 0; i < tasks.size(); i++) {
            Task toAdd = tasks.get(i);
            String taskType = toAdd.getTaskType();

            // ChatGPT recommended to add assertions here
            assert(taskType != null);
            assert(!taskType.isEmpty());
            assert(taskType.equals(TODO_TASK_PREFIX)
                   || taskType.equals(DEADLINE_TASK_PREFIX)
                   || taskType.equals(EVENT_TASK_PREFIX));

            String taskStrToAdd;

            switch (taskType) {
            case TODO_TASK_PREFIX:
                taskStrToAdd = createTodoDataString(toAdd);
                break;

            case DEADLINE_TASK_PREFIX:
                taskStrToAdd = createDeadlineDataString(toAdd);
                break;

            case "E":
                taskStrToAdd = createEventDataString(toAdd);
                break;

            default:
                System.out.println("Error: Invalid task type.");
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Something went wrong while closing the file writer.");
                }
                return false;
            }

            // add task string to buffer
            try {
                writer.write(taskStrToAdd);
                writer.write("\n");
            } catch (IOException e) {
                System.out.println("Something went wrong while writing to the file.");
                return false;
            }

        }

        // write data in buffer to file
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while closing the file writer.");
            return false;
        }

        return true;
    }

    /**
     * Create a string representation of an event task to be saved into the data file
     *
     * @param toAdd Task object to get data from
     * @return String data string of event task to be written into file
     */
    private String createEventDataString(Task toAdd) {
        int startTimeIndex = 0;
        int endTimeIndex = 1;
        int numTimes = 2;

        StringBuilder eventParameters = new StringBuilder();

        eventParameters.append(EVENT_TASK_PREFIX);
        eventParameters.append(SPACER);

        eventParameters.append((toAdd.isFinished() ? TASK_COMPLETED_STRING : TASK_INCOMPLETE_STRING));
        eventParameters.append(SPACER);

        eventParameters.append(toAdd.getTaskName());
        eventParameters.append(SPACER);

        String[] times = ((EventTask) toAdd).getTimesInput();
        // ChatGPT suggested assertion here
        assert(times.length == numTimes);
        assert(times[startTimeIndex] != null);
        assert(times[endTimeIndex] != null);

        eventParameters.append(times[startTimeIndex]).append(SPACER).append(times[endTimeIndex]);
        return eventParameters.toString();
    }

    /**
     * Create a string representation of a deadline task to be saved into the data file
     *
     * @param toAdd Task object to get data from
     * @return String data string of deadline task to be written into file
     */
    private String createDeadlineDataString(Task toAdd) {
        StringBuilder deadlineParameters = new StringBuilder();

        deadlineParameters.append(DEADLINE_TASK_PREFIX);
        deadlineParameters.append(SPACER);

        deadlineParameters.append((toAdd.isFinished() ? TASK_COMPLETED_STRING : TASK_INCOMPLETE_STRING));
        deadlineParameters.append(SPACER);

        deadlineParameters.append(toAdd.getTaskName());
        deadlineParameters.append(SPACER);

        deadlineParameters.append(((DeadlineTask) toAdd).getDeadlineInput());

        return deadlineParameters.toString();
    }

    /**
     * Create a string representation of a todo task to be saved into the data file
     *
     * @param toAdd Task object to get data from
     * @return String data string of todo task to be written into file
     */
    private String createTodoDataString(Task toAdd) {
        StringBuilder todoParameters = new StringBuilder();

        todoParameters.append(TODO_TASK_PREFIX);
        todoParameters.append(SPACER);

        todoParameters.append((toAdd.isFinished() ? TASK_COMPLETED_STRING : "0"));
        todoParameters.append(SPACER);

        todoParameters.append(toAdd.getTaskName());

        return todoParameters.toString();
    }


}
