package tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tasks.dao.TaskListDao;
import tasks.model.DoTaskForm;
import tasks.model.Task;
import tasks.model.TaskList;
import tasks.model.TaskToListForm;

import java.util.List;

@Controller
public class TaskListController {
    private final TaskListDao taskListDao;

    public TaskListController(TaskListDao taskListDao) {
        this.taskListDao = taskListDao;
    }

    @RequestMapping(value = "/get-task-lists", method = RequestMethod.GET)
    public String getTaskLists(ModelMap map) {
        List<TaskList> taskLists = taskListDao.getTaskLists();
        map.addAttribute("tasklists", taskLists);
        map.addAttribute("tasklist", new TaskList());
        map.addAttribute("tasktolist", new TaskToListForm());
        map.addAttribute("dotask", new DoTaskForm());
        return "index";
    }

    @RequestMapping(value = "/add-task-list", method = RequestMethod.POST)
    public String addTaskList(@ModelAttribute("tasklist") TaskList taskList) {
        taskListDao.addTaskList(taskList);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/add-task-to-list", method = RequestMethod.POST)
    public String addTaskToTaskList(
        @ModelAttribute("tasktolist") TaskToListForm taskToListForm
    ) {
        Task task = new Task();
        task.setName(taskToListForm.getTaskName());
        task.setDescription(taskToListForm.getTaskDesc());
        taskListDao.addTaskToTaskList(taskToListForm.getTaskListName(), task);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/do-task", method = RequestMethod.POST)
    public String doTask(
        @ModelAttribute("dotask") DoTaskForm doTaskForm
    ) {
        taskListDao.doTask(doTaskForm.getTaskListName(), doTaskForm.getTaskName());
        return "redirect:/get-task-lists";
    }
}
