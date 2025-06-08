package sia.taskmanager.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
}
