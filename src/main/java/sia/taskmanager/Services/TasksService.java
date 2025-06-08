package sia.taskmanager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Repositories.TaskRepository;

import java.util.List;

@Service
public class TasksService{
    private final TaskRepository taskRepository;
    @Autowired
    public TasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public List<Task> showTasks(User user) {
        return taskRepository.findByUserOrderByDueDateDesc(user);
    }

    public Task findByIdAndUser(int id, User user) {
        return taskRepository.findByIdAndUser(id, user);
    }

    @Transactional
    public void deleteTask(int id, User user) {
        taskRepository.deleteByIdAndUser(id, user);
    }

    public Task findTaskByPriority(Task.Priority priority, User user) {
        return taskRepository.findTasksByPriorityAndUser(priority, user);
    }
}
