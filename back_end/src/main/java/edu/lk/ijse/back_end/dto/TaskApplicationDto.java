package edu.lk.ijse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskApplicationDto {
    private long id;            // Application ID එක (Auto-generated)

    private long task_id;       // Task Entity එකේ ID එක

    private long employee_id;   // User (Employee) Entity එකේ ID එක

    private String status;// ApplicationStatus Enum එක String එකක් ලෙස (PENDING, ACCEPTED, etc.)
    private String title;
    private LocalDateTime appliedAt; // අයදුම් කළ වෙලාව
}
