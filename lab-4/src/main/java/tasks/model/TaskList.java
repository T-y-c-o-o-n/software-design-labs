package tasks.model;

import java.util.*;

public class TaskList {
    private String name;
    private Map<String, Task> tasks = new LinkedHashMap<>();

    public TaskList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return new HashSet<>(tasks.values());
    }

    public void addTask(Task task) {
        tasks.put(task.getName(), task);
    }

    public void doTask(String taskName) {
        Task task = tasks.get(taskName);
        task.setDone(!task.isDone());
    }
}
