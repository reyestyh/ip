package advisor;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class to store task data for each session of Advisor
 * Contains methods manipulating tasks within the TaskList
 */
public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    /**
     * Constructs a TaskList object to store tasks for the Advisor program,
     * and storage object for reading and writing to the data file
     * @param sto
     */
    public TaskList(Storage sto) {
        this.tasks = new ArrayList<>();
        this.storage = sto;
        // ChatGPT recommended assertion here
        assert(sto != null);
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
     *
     * @param data Array containing task type, description and relevant data required for creation
     * @return a new Task object
     */
    private Task createTask(String[] data) {
        // ChatGPT recommended assertion here
        assert(data != null);
        String completeIndicator = "1";
        int taskTypeIndex = 0;
        int completionIndex = 1;
        int descriptionIndex = 2;

        int deadlineIndex = 3;

        int startTimeIndex = 3;
        int endTimeIndex = 4;

        String taskType = data[taskTypeIndex];
        boolean isDone = data[completionIndex].equals(completeIndicator);
        try {
            switch (taskType) {
            case "T":
                return new ToDoTask(data[descriptionIndex], isDone);
            case "D":
                return new DeadlineTask(data[descriptionIndex], data[deadlineIndex], isDone);
            case "E":
                return new EventTask(data[descriptionIndex], data[startTimeIndex], data[endTimeIndex], isDone);
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
     *
     * @param task The task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the ArrayList
     *
     * @param index index of file position in ArrayList (0 based)
     * @return the removed Task
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns number of tasks in ArrayList
     *
     * @return number of tasks in current session
     */
    public int getNumTasks() {
        return tasks.size();
    }

    /**
     * Returns a given task at the specified index
     *
     * @param index index of file position in ArrayList (0 based)
     * @return Task object at index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as complete
     *
     * @param index index of task to be marked as complete (0 based)
     */
    public void completeTask(int index) {
        tasks.get(index).finishTask();
    }

    /**
     * Marks a task as incomplete
     *
     * @param index index of task to be marked as incomplete (0 based)
     */
    public void undoTask(int index) {
        tasks.get(index).undo();
    }

    /**
     * Finds tasks containing a search term in the task description
     * Returns a list containing matching tasks
     *
     * @param term search term
     * @return List containing matching tasks
     */
    public List<Task> findTasks(String term) {
        return this.tasks.stream().filter(task -> task.getTaskName()
                                                              .toLowerCase()
                                                                .contains(term.toLowerCase()))
                                    .toList();
    }

    /**
     * Updates file used to store tasks between sessions
     *
     * @return a boolean indicating successful update
     */
    public boolean updateStorage() {
        return storage.updateDataFile(this.tasks);
    }

    /**
     * Sorts the current tasks in the TaskList
     * Sorting is done in the following order: todo, deadline, event
     * Between todo tasks, in alphabetical order
     * Between deadline tasks, the one with the earliest date is 'lower'
     * Between event tasks, the one with the earlier start date is 'lower'
     */
    public void sortTasks() {

        class TaskComparator implements Comparator<Task> {
            public int compare(Task o1, Task o2) {
                return o1.compareTo(o2);
            }
        }

        this.tasks.sort(new TaskComparator());
    }

    /**
     * Returns a string of all the current tasks stored
     *
     * @return String of all tasks
     */
    public String getTasksString() {
        String emptyListMsg = "No tasks found.";
        if (this.tasks.isEmpty()) {
            return emptyListMsg;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.size() - 1; i++) {
            res.append(i + 1).append("  ").append(this.tasks.get(i).toString()).append("\n");
        }
        res.append(tasks.size()).append("  ").append(tasks.get(tasks.size() - 1).toString());
        return res.toString();
    }

}
