package tasks.dao;

import tasks.model.Task;
import tasks.model.TaskList;

import java.util.List;

public interface TaskListDao {
    List<TaskList> getTaskLists();
    void addTaskList(TaskList taskList);
    void deleteTaskList(TaskList taskList);
    void addTaskToTaskList(String taskListName, Task task);
    void doTask(String taskListName, String taskName);
}
