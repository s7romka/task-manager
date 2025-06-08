package sia.taskmanager.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false, updatable = false)
    private LocalDate dueDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public enum Priority{
        HIGH_PRIORITY("High priority"),
        MEDIUM_PRIORITY("Medium priority"),
        LOW_PRIORITY("Low priority");
        private final String displayString;
        Priority(String displayString) {
            this.displayString = displayString;
        }
        public String getDisplayString() {
            return displayString;
        }
    }

    public enum Status {
        OVERDUE("Overdue"),
        DEADLINE("Deadline"),
        IN_PROGRESS("In progress"),
        COMPLETED("The task is completed");
        private final String displayString;

        Status(String displayString) {
            this.displayString = displayString;
        }
        public String getDisplayString() {
            return displayString;
        }
    }
    public Status calculateStatus() {
        LocalDate today = LocalDate.now();
        if (this.status == Status.COMPLETED) {
            return Status.COMPLETED;
        }
        if (dueDate.isBefore(today)) {
            return Status.OVERDUE;
        }
        if (dueDate.isEqual(today)) {
            return Status.DEADLINE;
        }
        return Status.IN_PROGRESS;
    }

    public void markAsCompleted() {
        this.status = Status.COMPLETED;
    }
}
