package sia.taskmanager.Repositories;

import org.springframework.data.repository.CrudRepository;
import sia.taskmanager.Models.Task;
import sia.taskmanager.Models.User;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByUserOrderByDueDateDesc(User user);

    Task findByIdAndUser(int id, User user);

    void deleteByIdAndUser(int id, User user);

    Task findTasksByPriorityAndUser(Task.Priority priority, User user);
}
