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
public class EditTasksController {
    private final TasksService tasksService;

    @Autowired
    public EditTasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("/{id}/edit")
    public String loadEdit(@PathVariable int id, @AuthenticationPrincipal User user , Model model){
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
}
