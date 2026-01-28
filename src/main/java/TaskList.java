import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList(Storage sto) {
        this.tasks = new ArrayList<>();
        this.storage = sto;
    }



    private Task createTask(String[] data) {
        String taskType = data[0];
        boolean isDone = data[1].equals("1");
        try {
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
        } catch (DateTimeParseException d) {
            System.out.println("Error: Incorrect date format when reading from file.");
            return null;
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

}
