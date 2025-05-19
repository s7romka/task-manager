package sia.taskmanager.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.TasksService;

@Controller
@RequestMapping("/add")
@Slf4j
public class AddTaskController {
    private TasksService tasksService;
    @Autowired
    public AddTaskController(TasksService tasksService) {
        this.tasksService = tasksService;
    }
    @ModelAttribute("task")
    public Task task() {
        return new Task();
    }
    @GetMapping
    public String ShowAddTaskForm() {
        return "AddTaskForm";
    }
    @PostMapping
    public String AddTask(@ModelAttribute Task task, Errors errors , Authentication authentication) {
        if(errors.hasErrors()) {
            return "AddTaskForm";
        }
        User user = (User) authentication.getPrincipal();
        task.setUser(user);
        task.setStatus(task.getStatus());
        tasksService.saveTask(task);
        return "redirect:/tasks";
    }
}
