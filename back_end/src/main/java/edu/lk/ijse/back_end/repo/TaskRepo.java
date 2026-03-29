package edu.lk.ijse.back_end.repo;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    // 1. Hyper-Local: එකම දිස්ත්‍රික්කයේ පවතින වැඩ පමණක් සෙවීමට
    List<Task> findByDistrictAndStatus(String district, TaskStatus status);


    List<Task> getTasksByOwnerId(Long ownerId);
}
