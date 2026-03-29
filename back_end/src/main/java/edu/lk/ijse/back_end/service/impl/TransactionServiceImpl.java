package edu.lk.ijse.back_end.service.impl;

import edu.lk.ijse.back_end.dto.TransactionDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.Transaction;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.entity.enums.TaskStatus;
import edu.lk.ijse.back_end.repo.TaskRepo;
import edu.lk.ijse.back_end.repo.TransactionRepo;
import edu.lk.ijse.back_end.repo.UserRepo;
import edu.lk.ijse.back_end.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public void createTransaction(long taskId, double budget) {
        double systemFee = budget * 0.02;
        double studentPay = budget * 0.98;

        Transaction tx = new Transaction();
        tx.setTaskId(taskId);
        tx.setTotalBudget(budget);
        tx.setSystemFee(systemFee);
        tx.setStudentEarned(studentPay);

        transactionRepo.save(tx);
    }


    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepo.findAll().stream()
                .map(tx -> modelMapper.map(tx, TransactionDto.class))
                .collect(Collectors.toList());
    }
}