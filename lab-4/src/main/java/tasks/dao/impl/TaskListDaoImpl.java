package tasks.dao.impl;

import tasks.dao.TaskListDao;
import tasks.model.Task;
import tasks.model.TaskList;

import java.util.*;

public class TaskListDaoImpl implements TaskListDao {
    private final Map<String, TaskList> taskLists = new LinkedHashMap<>();

    @Override
    public List<TaskList> getTaskLists() {
        return new ArrayList<>(taskLists.values());
    }

    @Override
    public void addTaskList(TaskList taskList) {
        taskLists.put(taskList.getName(), taskList);
    }

    @Override
    public void deleteTaskList(TaskList taskList) {
        taskLists.remove(taskList.getName());
    }

    @Override
    public void addTaskToTaskList(String taskListName, Task task) {
        taskLists.get(taskListName).addTask(task);
    }

    @Override
    public void doTask(String taskListName, String taskName) {
        taskLists.get(taskListName).doTask(taskName);
    }
}
