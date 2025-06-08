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
@RequestMapping("/api/tasks")
public class EditTasksRestController {
    private final TasksService tasksService;

    @Autowired
    public EditTasksRestController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<String> editTasks(@PathVariable int id,
                                            @AuthenticationPrincipal User user,
                                            @Valid @RequestBody TaskRequest updatedTask) {
        Task existing = tasksService.findByIdAndUser(id, user);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(Task.Status.valueOf(updatedTask.getStatus()));
        existing.setPriority(Task.Priority.valueOf(updatedTask.getPriority()));
        existing.setDueDate(updatedTask.getDueDate());
        existing.setTitle(updatedTask.getTitle());
        tasksService.saveTask(existing);
        return ResponseEntity.ok("Task edited successfully");
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteTasks(@PathVariable int id, @AuthenticationPrincipal User user) {
        Task existing = tasksService.findByIdAndUser(id, user);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tasksService.deleteTask(id, user);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<String> completeTask(@PathVariable int id, @AuthenticationPrincipal User user) {
        Task existing = tasksService.findByIdAndUser(id, user);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setStatus(Task.Status.COMPLETED);
        tasksService.saveTask(existing);
        return ResponseEntity.ok("Task set as completed");
    }
}
