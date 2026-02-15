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
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + DATA_FILE_NAME + " not found.");
            return false;
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
            assert(taskType.equals("T") || taskType.equals("D") || taskType.equals("E"));

            String taskStrToAdd;

            switch (taskType) {
            case "T":
                taskStrToAdd = createTodoDataString(taskType, toAdd);
                break;

            case "D":
                taskStrToAdd = createDeadlineDataString(taskType, toAdd);
                break;

            case "E":
                taskStrToAdd = createEventDataString(taskType, toAdd);
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

    private String createEventDataString(String taskType, Task toAdd) {
        int startTimeIndex = 0;
        int endTimeIndex = 1;
        int numTimes = 2;

        StringBuilder eventParameters = new StringBuilder();

        eventParameters.append(taskType);
        eventParameters.append(SPACER);

        eventParameters.append((toAdd.isFinished() ? "1" : "0"));
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

    private String createDeadlineDataString(String taskType, Task toAdd) {
        StringBuilder deadlineParameters = new StringBuilder();

        deadlineParameters.append(taskType);
        deadlineParameters.append(SPACER);

        deadlineParameters.append((toAdd.isFinished() ? "1" : "0"));
        deadlineParameters.append(SPACER);

        deadlineParameters.append(toAdd.getTaskName());
        deadlineParameters.append(SPACER);

        deadlineParameters.append(((DeadlineTask) toAdd).getDeadlineInput());

        return deadlineParameters.toString();
    }

    private String createTodoDataString(String taskType, Task toAdd) {
        StringBuilder todoParameters = new StringBuilder();

        todoParameters.append(taskType);
        todoParameters.append(SPACER);

        todoParameters.append((toAdd.isFinished() ? "1" : "0"));
        todoParameters.append(SPACER);

        todoParameters.append(toAdd.getTaskName());

        return todoParameters.toString();
    }


}
