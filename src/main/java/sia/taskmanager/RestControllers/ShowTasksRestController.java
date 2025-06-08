package sia.taskmanager.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sia.taskmanager.DTO.TaskResponse;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Services.TasksService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class ShowTasksRestController {
    private final TasksService tasksService;

    @Autowired
    public ShowTasksRestController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@AuthenticationPrincipal User user) {
        List<Task> tasks = tasksService.showTasks(user);

        List<TaskResponse> response = tasks.stream().map(
                task -> {
                    TaskResponse responseDTO = new TaskResponse();
                    responseDTO.setId(task.getId());
                    responseDTO.setDescription(task.getDescription());
                    responseDTO.setDueDate(task.getDueDate());
                    responseDTO.setPriority(task.getPriority().getDisplayString());
                    responseDTO.setStatus(task.getStatus().getDisplayString());
                    return responseDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
