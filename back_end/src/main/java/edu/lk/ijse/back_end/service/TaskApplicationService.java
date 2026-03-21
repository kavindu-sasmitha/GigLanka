package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.TaskApplicationDto;

import java.util.List;

public interface TaskApplicationService {
    void saveApplication(TaskApplicationDto dto);
    List<TaskApplicationDto> getApplicationsByEmployee(long employeeId);
    List<TaskApplicationDto> getAllApplications();
    void updateStatus(long id, String status);
    void deleteApplication(long id);
}

