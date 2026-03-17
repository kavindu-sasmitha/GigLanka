package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void saveTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    void updateTask(TaskDto taskDto);
    void deleteTaskById(Long id);
}
