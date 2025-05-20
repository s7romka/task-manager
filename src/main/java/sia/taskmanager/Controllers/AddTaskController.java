package sia.taskmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.TasksService;

@Controller
@RequestMapping("/add")
@Slf4j
public class AddTaskController {
    private final TasksService tasksService;
    @Autowired
    public AddTaskController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @ModelAttribute("task")
    public Task task() {
        return new Task();
    }

    @GetMapping
    public String loadAddTaskForm() {
        return "add-task";
    }

    @PostMapping
    public String processTask(@ModelAttribute Task task, Errors errors , Authentication authentication) {
        if(errors.hasErrors()) {
            return "add-task";
        }
        User user = (User) authentication.getPrincipal();
        task.setUser(user);
        task.setStatus(task.getStatus());
        tasksService.saveTask(task);
        return "redirect:/tasks";
    }
}
