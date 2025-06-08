package sia.taskmanager.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordResetDTO {
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Your password must have at least one capital letter and digit: ")
    private String password;
    @NotNull
    private String confirmPassword;

}
