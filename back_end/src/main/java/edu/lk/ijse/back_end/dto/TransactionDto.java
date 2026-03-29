package edu.lk.ijse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private long id;
    private long taskId;
    private double totalBudget;
    private double systemFee;
    private double studentEarned;
    private LocalDateTime timestamp;
}