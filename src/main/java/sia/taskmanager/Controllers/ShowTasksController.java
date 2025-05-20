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

    @GetMapping("/{id}/edit")
    public String loadEdit(@PathVariable int id, @AuthenticationPrincipal User user ,Model model){
        Task task = tasksService.findByIdAndUser(id, user);
        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/{id}/edit")
    public String processEdit(@AuthenticationPrincipal User user, @PathVariable int id, @ModelAttribute Task task, Model model){
        Task existing = tasksService.findByIdAndUser(id, user);
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        model.addAttribute("task", existing);
        tasksService.saveTask(existing);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@AuthenticationPrincipal User user, @PathVariable int id){
        tasksService.deleteTask(id,user);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String setCompleted(@AuthenticationPrincipal User user, @PathVariable int id, @ModelAttribute Task task){
        Task existing = tasksService.findByIdAndUser(id, user);
        existing.markAsCompleted();
        tasksService.saveTask(existing);
        return "redirect:/tasks";
    }

    @GetMapping("/filter/")
    public String filterTasksByPriority(@AuthenticationPrincipal User user, @RequestParam(required = false) Task.Priority priority, @ModelAttribute Task task, Model model){
        if(priority == null) return "redirect:/tasks";
        model.addAttribute("tasks", tasksService.findTaskByPriority(priority, user));
        return "tasks";
    }
}

