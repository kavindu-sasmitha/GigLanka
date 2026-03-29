package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.TransactionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    void createTransaction(long taskId, double budget);


    List<TransactionDto> getAllTransactions();
}