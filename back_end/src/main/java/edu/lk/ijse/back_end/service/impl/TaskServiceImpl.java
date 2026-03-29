package edu.lk.ijse.back_end.service.impl;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.Transaction;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.entity.enums.TaskStatus;
import edu.lk.ijse.back_end.repo.TaskRepo;
import edu.lk.ijse.back_end.repo.TransactionRepo;
import edu.lk.ijse.back_end.repo.UserRepo;
import edu.lk.ijse.back_end.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public void saveTask(TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("TaskDto is null");
        }

        Task task = modelMapper.map(taskDto, Task.class);
        User owner = userRepo.findById(taskDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + taskDto.getOwnerId()));

        // 3. Task එකට අදාළ User (owner) ව set කරන්න
        task.setOwner(owner);

        // 4. දැන් සම්පූර්ණ Task එක save කරන්න
        taskRepo.save(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepo.findAll().stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        if(taskDto == null || taskDto.getId() == null) {
            throw new IllegalArgumentException("Task ID is required for update");
        }

        // DB එකේ දැනට තියෙන Task එක තියෙනවාද කියලා බලන්න (Optional but recommended)
        if (!taskRepo.existsById(taskDto.getId())) {
            throw new RuntimeException("Task not found with ID: " + taskDto.getId());
        }

        // Map DTO to Entity
        Task task = modelMapper.map(taskDto, Task.class);

        // Save the entity
        taskRepo.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepo.deleteById(id);
    }

    @Override
    public List<TaskDto> getAllTasksByOwnerId(Long id) {
        // 1. Repository එක හරහා අදාළ Owner ට අයිති Task Entity ලැයිස්තුව ලබා ගැනීම
        List<Task> tasks = taskRepo.getTasksByOwnerId(id);

        // 2. Entity ලැයිස්තුව TaskDto ලැයිස්තුවකට Map කිරීම
        return tasks.stream()
                .map(task -> {
                    TaskDto dto = modelMapper.map(task, TaskDto.class);

                    // Entity එකේ 'owner' object එකක් ඇති නිසා,
                    // එහි ID එක අරන් DTO එකේ 'owner_id' එකට manual සෙට් කරන්න
                    if (task.getOwner() != null) {
                        dto.setOwnerId(task.getOwner().getId());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void flashMatch(long taskId, long employeeId) {
        Task task = taskRepo.findById(taskId).orElseThrow();

        // Flash Match Logic: පළමු ශිෂ්‍යයා තහවුරු වූ සැණින් වෙනත් අයට අවස්ථාව වැසේ
        if (task.getStatus() == TaskStatus.PENDING) {
            User employee = userRepo.findById(employeeId).orElseThrow();
            task.setAcceptedEmployee(employee);
            task.setStatus(TaskStatus.ONGOING);
            taskRepo.save(task);
        } else {
            throw new RuntimeException("Task already taken by someone else!");
        }
    }

    @Override
    public void completeTask(long taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow();
        if (task.getStatus() != TaskStatus.ONGOING) return;

        double budget = task.getBudget();

        // 3. Automated Revenue Logic: 2% System Fee & 98% Student Payment
        double systemFee = budget * 0.02;
        double studentPay = budget * 0.98;

        // Student Wallet එකට මුදල් බැර කිරීම
        User student = task.getAcceptedEmployee();
        student.setWalletBalance(student.getWalletBalance() + studentPay);

        task.setStatus(TaskStatus.COMPLETED);

        // Transaction වාර්තාවක් තබා ගැනීම
        Transaction tx = new Transaction();
        tx.setTaskId(taskId);
        tx.setTotalBudget(budget);
        tx.setSystemFee(systemFee);
        tx.setStudentEarned(studentPay);

        taskRepo.save(task);
        userRepo.save(student);
        transactionRepo.save(tx);
    }

    @Override
    public List<TaskDto> getTasksByDistrict(String district) {
        return taskRepo.findByDistrictAndStatus(district, TaskStatus.PENDING)
                .stream().map(t -> modelMapper.map(t, TaskDto.class))
                .collect(Collectors.toList());
    }


}
