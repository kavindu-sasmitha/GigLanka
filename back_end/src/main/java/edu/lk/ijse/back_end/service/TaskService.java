package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void saveTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    void updateTask(TaskDto taskDto);
    void deleteTaskById(Long id);

    List<TaskDto> getAllTasksByOwnerId(Long ownerId);
    void flashMatch(long taskId, long employeeId); // Flash Match Feature
    void completeTask(long taskId); // Revenue & Escrow Logic
    List<TaskDto> getTasksByDistrict(String district); // Local Alerts
    List<TaskDto> getCompletedTasksByEmployee(long employeeId);
}
