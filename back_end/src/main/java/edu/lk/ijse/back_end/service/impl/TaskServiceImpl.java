package edu.lk.ijse.back_end.service.impl;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.Transaction;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.entity.enums.TaskStatus;
import edu.lk.ijse.back_end.exception.NotFoundException;
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
            throw new IllegalArgumentException("Task data cannot be null");
        }

        Task task = modelMapper.map(taskDto, Task.class);
        User owner = userRepo.findById(taskDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Task Owner not found with ID: " + taskDto.getOwnerId()));

        task.setOwner(owner);
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

        if (!taskRepo.existsById(taskDto.getId())) {
            throw new NotFoundException("Task not found with ID: " + taskDto.getId());
        }

        Task task = modelMapper.map(taskDto, Task.class);
        taskRepo.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        if (!taskRepo.existsById(id)) {
            throw new NotFoundException("Cannot delete. Task not found with ID: " + id);
        }
        taskRepo.deleteById(id);
    }

    @Override
    public List<TaskDto> getAllTasksByOwnerId(Long id) {
        List<Task> tasks = taskRepo.getTasksByOwnerId(id);
        return tasks.stream()
                .map(task -> {
                    TaskDto dto = modelMapper.map(task, TaskDto.class);
                    if (task.getOwner() != null) {
                        dto.setOwnerId(task.getOwner().getId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void flashMatch(long taskId, long employeeId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));

        if (task.getStatus() == TaskStatus.PENDING) {
            User employee = userRepo.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + employeeId));

            task.setAcceptedEmployee(employee);
            task.setStatus(TaskStatus.ONGOING);
            taskRepo.save(task);
        } else {
            // Task එක දැනටමත් වෙන කෙනෙක් අරන් නම් Conflict error එකක් වගේ දෙයක් දිය හැක
            throw new RuntimeException("Flash Match Failed: Task is already " + task.getStatus());
        }
    }

    @Override
    public void completeTask(long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));

        if (task.getStatus() != TaskStatus.ONGOING) {
            throw new RuntimeException("Only ongoing tasks can be marked as completed.");
        }

        double budget = task.getBudget();
        double systemFee = budget * 0.02;
        double studentPay = budget * 0.98;

        User student = task.getAcceptedEmployee();
        if (student == null) {
            throw new RuntimeException("No employee assigned to this task.");
        }

        student.setWalletBalance(student.getWalletBalance() + studentPay);
        task.setStatus(TaskStatus.COMPLETED);

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

    @Override
    public List<TaskDto> getCompletedTasksByEmployee(long employeeId) {
        List<Task> tasks = taskRepo.findByAcceptedEmployeeIdAndStatus(employeeId, TaskStatus.COMPLETED);
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }
}