package edu.lk.ijse.back_end.service.impl;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.repo.TaskRepo;
import edu.lk.ijse.back_end.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void saveTask(TaskDto taskDto) {
        if (taskDto==null){
            throw new IllegalArgumentException("TaskDto is null");
        }
        taskRepo.save(modelMapper.map(taskDto, Task.class));
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return List.of();
    }

    @Override
    public void updateTask(TaskDto taskDto) {

    }

    @Override
    public void deleteTaskById(Long id) {

    }
}
