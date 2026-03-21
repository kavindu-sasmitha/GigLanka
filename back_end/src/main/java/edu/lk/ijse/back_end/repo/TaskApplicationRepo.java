package edu.lk.ijse.back_end.repo;

import edu.lk.ijse.back_end.entity.TaskApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskApplicationRepo extends JpaRepository<TaskApplication, Long> {
    // යම් employee කෙනෙක් apply කළ සියලුම tasks සෙවීමට
    List<TaskApplication> findByEmployeeId(long employeeId);
}