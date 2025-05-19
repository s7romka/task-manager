package sia.taskmanager.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.taskmanager.Models.User;
import sia.taskmanager.Repositories.UserRepository;
import sia.taskmanager.Validators.AuthValidator;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return user;
    }
}
