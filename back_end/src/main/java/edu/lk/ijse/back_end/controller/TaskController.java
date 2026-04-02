package edu.lk.ijse.back_end.controller;

import edu.lk.ijse.back_end.util.ApiResponse;
import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.saveTask(taskDto);
        return new ResponseEntity<>(new ApiResponse<>(201, "Task saved", null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.updateTask(taskDto);
        return new ResponseEntity<>(new ApiResponse<>(200, "Task updated", null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(new ApiResponse<>(200, "Task deleted", null), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{owner_id}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getAllTasksByUserId(@PathVariable Long owner_id) {
        List<TaskDto> taskList = taskService.getAllTasksByOwnerId(owner_id);
        return new ResponseEntity<>(new ApiResponse<>(200, "Tasks fetched successfully", taskList), HttpStatus.OK);
    }


    @GetMapping("/nearby/{district}")
    public ResponseEntity<List<TaskDto>> getNearbyTasks(@PathVariable String district) {
        return ResponseEntity.ok(taskService.getTasksByDistrict(district));
    }


    @PostMapping("/accept/{taskId}/{employeeId}")
    public ResponseEntity<ApiResponse<String>> acceptTask(@PathVariable long taskId, @PathVariable long employeeId) {
        taskService.flashMatch(taskId, employeeId);
        return new ResponseEntity<>(new ApiResponse<>(
                200, "Task accepted and status changed to ONGOING", null
        ), HttpStatus.OK);
    }


    @PostMapping("/complete/{taskId}")
    public ResponseEntity<ApiResponse<String>> completeTask(@PathVariable long taskId) {
        taskService.completeTask(taskId);
        return new ResponseEntity<>(new ApiResponse<>(
                200, "Payment processed and Task completed successfully", null
        ), HttpStatus.OK);
    }
    @GetMapping("/completed/{employeeId}")
    public ResponseEntity<List<TaskDto>> getCompletedTasks(@PathVariable long employeeId) {
        List<TaskDto> completedTasks = taskService.getCompletedTasksByEmployee(employeeId);
        return ResponseEntity.ok(completedTasks);
    }
}