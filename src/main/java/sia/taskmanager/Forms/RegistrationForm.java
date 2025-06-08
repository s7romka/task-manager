package sia.taskmanager.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.taskmanager.Models.User;

import java.util.Date;
import java.util.UUID;

@Data
public class RegistrationForm {
    @NotBlank(message = "Login is required")
    private String login;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must have at least one capital letter and digit")
    private String password;
    @NotBlank(message = "Username is required")
    private String username;
    private String email;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(login, username, email, new Date());
        user.setPassword(passwordEncoder.encode(password));
        user.setToken(UUID.randomUUID().toString());
        user.setEnabled(false);
        return user;
    }
}
