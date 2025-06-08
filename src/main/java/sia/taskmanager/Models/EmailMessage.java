package sia.taskmanager.Models;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmailMessage implements Serializable {
    private String to;
    private String subject;
    private String templateName;
    private String context;
    @Pattern(regexp = "CONFIRMATION|PASSWORD_RESET|TASK_REMINDER")
    private String type;
}
