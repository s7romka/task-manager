package sia.taskmanager.RestControllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sia.taskmanager.DTO.TaskRequest;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.TasksService;

@RestController
@RequestMapping("/api/add")
public class AddTaskRestController {
    private final TasksService tasksService;

    @Autowired
    public AddTaskRestController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping
    public ResponseEntity<String> processTask(@AuthenticationPrincipal User user, @Valid @RequestBody TaskRequest taskdto) {
        Task task = new Task();
        task.setTitle(taskdto.getTitle());
        task.setDescription(taskdto.getDescription());
        task.setDueDate(taskdto.getDueDate());
        try{
            task.setPriority(Task.Priority.valueOf(taskdto.getPriority()));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(
                    "Invalid priority. Use 'Low priority', 'Medium priority', 'High priority'"
            );
        }
        task.setStatus(task.calculateStatus());
        task.setUser(user);
        tasksService.saveTask(task);
        return ResponseEntity.ok("Task added successfully");
    }
}
