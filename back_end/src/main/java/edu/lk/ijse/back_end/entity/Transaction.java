package edu.lk.ijse.back_end.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long taskId;
    private double totalBudget;
    private double systemFee;    // 2%
    private double studentEarned; // 98%
    private LocalDateTime timestamp = LocalDateTime.now();
}