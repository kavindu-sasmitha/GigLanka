package edu.lk.ijse.back_end.service.impl;

;
import edu.lk.ijse.back_end.dto.TaskApplicationDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.TaskApplication;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.entity.enums.ApplicationStatus;
import edu.lk.ijse.back_end.exception.NotFoundException;
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
        // RuntimeException වෙනුවට NotFoundException භාවිතා කරන ලදී
        Task task = taskRepository.findById(dto.getTask_id())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTask_id()));

        User user = userRepository.findById(dto.getEmployee_id())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getEmployee_id()));

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
        // Application එක නැති විට Custom NotFoundException එක throw කරයි
        TaskApplication app = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found with ID: " + id));

        try {
            app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            // වැරදි Status එකක් එවුවොත් handle කිරීමට (Optional)
            throw new RuntimeException("Invalid status provided: " + status);
        }

        repository.save(app);
    }

    @Override
    public void deleteApplication(long id) {
        // Delete කිරීමට පෙර id එක තියෙනවද බලන්න පුළුවන් (Safe practice)
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cannot delete. Application not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<TaskApplicationDto> getAllTasksByOwnerId(long ownerId) {

        List<TaskApplication> applyTaskList = repository.findByTaskOwnerId(ownerId);

        return applyTaskList.stream().map(taskApplication -> {
            TaskApplicationDto dto = new TaskApplicationDto();


            dto.setId(taskApplication.getId());
            dto.setStatus(taskApplication.getStatus().toString());


            if (taskApplication.getTask() != null) {
                dto.setTask_id(taskApplication.getTask().getId());
                dto.setTitle(taskApplication.getTask().getTitle());
            }

            if (taskApplication.getEmployee() != null) {
                dto.setEmployee_id(taskApplication.getEmployee().getId());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}