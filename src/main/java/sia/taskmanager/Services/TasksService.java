package sia.taskmanager.Services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;
import sia.taskmanager.Repositories.TaskRepository;
import sia.taskmanager.Repositories.UserRepository;

import java.util.List;

@Service
public class TasksService{
    private final UserRepository userRepository;
    private TaskRepository taskRepository;
    @Autowired
    public TasksService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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
