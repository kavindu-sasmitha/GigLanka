package edu.lk.ijse.back_end.repo;

import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {


    List<Task> getTasksByOwnerId(Long ownerId);
}
