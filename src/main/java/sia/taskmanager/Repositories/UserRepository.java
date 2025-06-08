package sia.taskmanager.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sia.taskmanager.Models.User;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
     Optional<User> findByLogin(String login);
     Optional<User> findByToken(String token);
     Optional<User> findByEmail(String email);
}
