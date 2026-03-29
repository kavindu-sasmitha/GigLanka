package edu.lk.ijse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskApplicationDto {
    private long id;            // Application ID(Auto-generated)

    private long task_id;       // Task Entity ID

    private long employee_id;   // User (Employee) Entity ID
    private String full_name;
    private String status;// ApplicationStatus Enum String (PENDING, ACCEPTED, etc.)
    private String title;
    private LocalDateTime appliedAt;
}
