package edu.lk.ijse.back_end.entity;

import edu.lk.ijse.back_end.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private double budget;
    private String district;
    private double latitude;
    private double phoneNumber;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "accepted_employee_id")
    private User acceptedEmployee; // New: Flash Match result

    private LocalDateTime createdAt = LocalDateTime.now();
}