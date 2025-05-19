package sia.taskmanager.Forms;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.taskmanager.Models.User;

import java.util.Date;

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
        return new User(login, passwordEncoder.encode(password), username, email, new Date());
    }
}
