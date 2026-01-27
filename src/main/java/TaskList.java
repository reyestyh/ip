import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TaskList {

    private ArrayList<Task> tasks;
    public static String DATA_FILE_NAME = "./AdvisorTaskData.txt";

    public TaskList() {
        this.tasks = new ArrayList<>();
        this.readDataFile();
    }

    public void readDataFile() {
        System.out.println(System.getProperty("user.dir"));
        File dataFile = new File(DATA_FILE_NAME);
        try {
            Scanner reader = new Scanner(dataFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] temp = data.split(";;;");
                this.addTask(createTask(temp));
            }
            reader.close();
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

    }

    private Task createTask(String[] data) {
        String taskType = data[0];
        boolean isDone = data[1].equals("1");
        switch (taskType) {
        case "T":
            return new ToDoTask(data[2], isDone);
        case "D":
            return new DeadlineTask(data[2], data[3], isDone);
        case "E":
            return new EventTask(data[2], data[3], data[4], isDone);
        default:
            System.out.println("Error: Invalid task type.");
            break;
        }
        return null;
    }


    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public int getNumTasks() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void completeTask(int index) {
        tasks.get(index).finishTask();
    }

    public void undoTask(int index) {
        tasks.get(index).undo();
    }

    public String getTasksString() {
        if (this.tasks.isEmpty()) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.size() - 1; i++) {
            res.append(this.tasks.get(i).toString()).append("\n");
        }
        res.append(tasks.get(tasks.size() - 1).toString());
        return res.toString();
    }

    public boolean updateDataFile() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(DATA_FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + DATA_FILE_NAME + " not found.");
            return false;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
                res.append(((DeadlineTask) toAdd).getDeadline());

            } else if (taskType.equals("E")) {
                res.append(taskType);
                res.append(";;;");
                res.append((toAdd.isFinished() ? "1" : "0"));
                res.append(";;;");
                res.append(toAdd.getTaskName());
                res.append(";;;");
                String[] times = ((EventTask) toAdd).getTimes();
                res.append(times[0] + ";;;" + times[1]);
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
                e.printStackTrace();
            }

        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while closing the file writer.");
        }

        return true;
    }

}
