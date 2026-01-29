package advisor;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList(Storage sto) {
        this.tasks = new ArrayList<>();
        this.storage = sto;
    }

    /**
     * Updates tasks ArrayList after reading tasks stored in data file
     */
    public void populateList() {
        ArrayList<String[]> dataArr = this.storage.readDataFile();
        for (int i = 0; i < dataArr.size(); i++) {
            tasks.add(createTask(dataArr.get(i)));
        }
    }

    /**
     * Returns a Task from given parameters for creation
     * @param data Array containing task type, description and relevant data required for creation
     * @return a new Task object
     */
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

    /**
     * Adds a task into the ArrayList
     * @param task The task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the ArrayList
     * @param index index of file position in ArrayList (0 based)
     * @return the removed Task
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns number of tasks in ArrayList
     * @return number of tasks in current session
     */
    public int getNumTasks() {
        return tasks.size();
    }

    /**
     * Returns a given task at the specified index
     * @param index index of file position in ArrayList (0 based)
     * @return Task object at index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as complete
     * @param index index of task to be marked as complete (0 based)
     */
    public void completeTask(int index) {
        tasks.get(index).finishTask();
    }

    /**
     * Marks a task as incomplete
     * @param index index of task to be marked as incomplete (0 based)
     */
    public void undoTask(int index) {
        tasks.get(index).undo();
    }

    /**
     * Updates file used to store tasks between sessions
     * @return a boolean indicating successful update
     */
    public List<Task> findTasks(String term) {
        return this.tasks.stream().filter(task -> task.getTaskName().toLowerCase().contains(term.toLowerCase())).toList();
    }

    public boolean updateStorage() {
        return storage.updateDataFile(this.tasks);
    }

    /**
     * Returns a string of all the current tasks stored
     * @return String of all tasks
     */
    public String getTasksString() {
        if (this.tasks.isEmpty()) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.size() - 1; i++) {
            res.append(i + 1).append("  ").append(this.tasks.get(i).toString()).append("\n");
        }
        res.append(tasks.size()).append("  ").append(tasks.get(tasks.size() - 1).toString());
        return res.toString();
    }

}
