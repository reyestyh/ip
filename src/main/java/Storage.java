import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    public static String DATA_FILE_NAME = "./AdvisorTaskData.txt";

    public ArrayList<String[]> readDataFile() {
        ArrayList<String[]> res = new ArrayList<>();
        File dataFile = new File(DATA_FILE_NAME);
        try {
            Scanner reader = new Scanner(dataFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data != "") {
                    String[] temp = data.split(";;;");
                    if (temp != null) {
                        res.add(temp);
                    }
                }

            }
            reader.close();
            return res;
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
        return res;
    }

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
        for (Task toAdd : tasks) {
            StringBuilder res = new StringBuilder();
            String taskType = toAdd.getTaskType();

            if (taskType.equals("T")) {
                res.append(taskType);
                res.append(";;;");
                res.append((toAdd.isFinished() ? "1" : "0"));
                res.append(";;;");
                res.append(toAdd.getTaskName());

            } else if (taskType.equals("D")) {
                res.append(taskType);
                res.append(";;;");
                res.append((toAdd.isFinished() ? "1" : "0"));
                res.append(";;;");
                res.append(toAdd.getTaskName());
                res.append(";;;");
                res.append(((DeadlineTask) toAdd).getDeadlineInput());

            } else if (taskType.equals("E")) {
                res.append(taskType);
                res.append(";;;");
                res.append((toAdd.isFinished() ? "1" : "0"));
                res.append(";;;");
                res.append(toAdd.getTaskName());
                res.append(";;;");
                String[] times = ((EventTask) toAdd).getTimesInput();
                res.append(times[0]).append(";;;").append(times[1]);
            } else {
                System.out.println("Error: Invalid task type.");
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Something went wrong while closing the file writer.");
                }
                return false;
            }
            try {
                writer.write(res.toString());
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
