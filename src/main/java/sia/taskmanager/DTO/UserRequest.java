package sia.taskmanager.DTO;

import lombok.Data;

@Data
public class UserRequest {
    private String login;
    private String password;
    private String username;
    private String email;
}
