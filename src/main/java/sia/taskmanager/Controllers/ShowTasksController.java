package sia.taskmanager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.TasksService;

@Controller
@RequestMapping("/tasks")
@SessionAttributes("user")
public class ShowTasksController {
    private final TasksService tasksService;
    @Autowired
    public ShowTasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public String loadTasks(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("tasks", tasksService.showTasks(user));
        return "tasks";
    }

    @GetMapping("/filter/")
    public String filterTasksByPriority(@AuthenticationPrincipal User user, @RequestParam(required = false) Task.Priority priority, @ModelAttribute Task task, Model model){
        if(priority == null) return "redirect:/tasks";
        model.addAttribute("tasks", tasksService.findTaskByPriority(priority, user));
        return "tasks";
    }
}

