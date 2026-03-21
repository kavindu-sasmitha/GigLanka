package edu.lk.ijse.back_end.service.impl;

;
import edu.lk.ijse.back_end.dto.TaskApplicationDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.TaskApplication;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.entity.enums.ApplicationStatus;
import edu.lk.ijse.back_end.repo.TaskApplicationRepo;
import edu.lk.ijse.back_end.repo.TaskRepo;
import edu.lk.ijse.back_end.repo.UserRepo;
import edu.lk.ijse.back_end.service.TaskApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskApplicationServiceImpl implements TaskApplicationService {

    private final TaskApplicationRepo repository;
    private final TaskRepo taskRepository;
    private final UserRepo userRepository;
    private final ModelMapper mapper;

    @Override
    public void saveApplication(TaskApplicationDto dto) {
        // Task එක සහ User එක database එකෙන් හොයාගන්නවා
        Task task = taskRepository.findById(dto.getTask_id())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(dto.getEmployee_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TaskApplication application = new TaskApplication();
        application.setTask(task);
        application.setEmployee(user);
        application.setStatus(ApplicationStatus.PENDING);

        repository.save(application);
    }

    @Override
    public List<TaskApplicationDto> getApplicationsByEmployee(long employeeId) {
        return repository.findByEmployeeId(employeeId).stream()
                .map(app -> {
                    TaskApplicationDto dto = mapper.map(app, TaskApplicationDto.class);
                    dto.setTask_id(app.getTask().getId());
                    dto.setEmployee_id(app.getEmployee().getId());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<TaskApplicationDto> getAllApplications() {
        return repository.findAll().stream()
                .map(app -> {
                    TaskApplicationDto dto = mapper.map(app, TaskApplicationDto.class);
                    dto.setTask_id(app.getTask().getId());
                    dto.setEmployee_id(app.getEmployee().getId());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(long id, String status) {
        TaskApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        repository.save(app);
    }

    @Override
    public void deleteApplication(long id) {
        repository.deleteById(id);
    }
}