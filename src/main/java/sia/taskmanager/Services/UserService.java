package sia.taskmanager.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sia.taskmanager.Models.EmailMessage;
import sia.taskmanager.Models.User;
import sia.taskmanager.Repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public UserService(UserRepository userRepository, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public void saveAndConfirm(User user) {
        userRepository.save(user);
        sendConfirmationEmail(user);
    }
    public void save(User user) {
        userRepository.save(user);
    }

    public void resetPassword(String token, String password) {
        User user = userRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));
        user.setPassword(password);
        user.setToken(null);
        userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }
    @Transactional
    public void sendResetPasswordEmail(Optional<User> optionalUser){
        String token = UUID.randomUUID().toString();
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("Something went wrong"));
        user.setToken(token);
        userRepository.save(user);
        log.info("Saved reset token {} for user {}", token, user.getId());

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(user.getEmail());
        emailMessage.setSubject("Reset Password Email");
        emailMessage.setTemplateName("email/password-reset");
        emailMessage.setContext("http://localhost:8080/reset-password?token=" + user.getToken());
        emailMessage.setType("RESET-PASSWORD");
        kafkaProducerService.sendEmailMessage(emailMessage);
        log.info("Sending reset link: {} to email: {}", emailMessage.getContext(), user.getEmail() );
    }

    private void sendConfirmationEmail(User user) {
        EmailMessage message = new EmailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirmation Email");
        message.setTemplateName("email/confirmation");
        message.setContext("http://localhost:8080/register/confirm?token=" + user.getToken());
        message.setType("CONFIRMATION");
        kafkaProducerService.sendEmailMessage(message);
    }
    public boolean confirm(String token) {
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setEnabled(true);
            user1.setToken(null);
            userRepository.save(user1);
            return true;
        }
        return false;
    }
}
