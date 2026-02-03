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

    public static String DATA_FILE_NAME = "./AdvisorTaskData.txt";

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
                String data = reader.nextLine();
                if (!data.isEmpty()) {
                    String[] temp = data.split(";;;");
                    if (temp != null) {
                        tasksStrsList.add(temp);
                    }
                }

            }
            reader.close();
            return tasksStrsList;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + DATA_FILE_NAME + " not found.");
            System.out.println("Creating " + DATA_FILE_NAME + " in the current directory: "
                    + System.getProperty("user.dir"));
            try {
                dataFile.createNewFile();
            } catch (IOException d) {
                System.out.println("An error occurred.");
                d.printStackTrace();
            }

        }
        return tasksStrsList;
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
            Task toAdd =  tasks.get(i);
            StringBuilder taskParameters = new StringBuilder();
            String taskType = toAdd.getTaskType();

            switch(taskType) {
            case "T":
                taskParameters.append(taskType);
                taskParameters.append(";;;");
                taskParameters.append((toAdd.isFinished() ? "1" : "0"));
                taskParameters.append(";;;");
                taskParameters.append(toAdd.getTaskName());
                break;

            case "D":
                taskParameters.append(taskType);
                taskParameters.append(";;;");
                taskParameters.append((toAdd.isFinished() ? "1" : "0"));
                taskParameters.append(";;;");
                taskParameters.append(toAdd.getTaskName());
                taskParameters.append(";;;");
                taskParameters.append(((DeadlineTask) toAdd).getDeadlineInput());
                break;

            case "E":
                taskParameters.append(taskType);
                taskParameters.append(";;;");
                taskParameters.append((toAdd.isFinished() ? "1" : "0"));
                taskParameters.append(";;;");
                taskParameters.append(toAdd.getTaskName());
                taskParameters.append(";;;");
                String[] times = ((EventTask) toAdd).getTimesInput();
                taskParameters.append(times[0]).append(";;;").append(times[1]);
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

            try {
                writer.write(taskParameters.toString());
                writer.write("\n");
            } catch (IOException e) {
                System.out.println("Something went wrong while writing to the file.");
                return false;
            }

        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while closing the file writer.");
            return false;
        }

        return true;
    }


}
