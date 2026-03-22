package edu.lk.ijse.back_end.service.impl;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.repo.TaskRepo;
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

    @Override
    public void saveTask(TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("TaskDto is null");
        }

        Task task = modelMapper.map(taskDto, Task.class);
        User owner = userRepo.findById(taskDto.getOwner_id())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + taskDto.getOwner_id()));

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

    }

    @Override
    public void deleteTaskById(Long id) {

    }
}
