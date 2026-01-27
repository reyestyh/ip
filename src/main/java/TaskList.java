import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {

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
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tasks.size() - 1; i++) {
            res.append(this.tasks.get(i).toString()).append("\n");
        }
        res.append(tasks.get(tasks.size() - 1).toString());
        return res.toString();
    }

}
