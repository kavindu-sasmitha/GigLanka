package edu.lk.ijse.back_end.controller;


import edu.lk.ijse.back_end.dto.TaskApplicationDto;
import edu.lk.ijse.back_end.dto.TaskDto;
import edu.lk.ijse.back_end.service.TaskApplicationService;
import edu.lk.ijse.back_end.service.TaskService;
import edu.lk.ijse.back_end.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/application")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskApplicationController {
@Autowired
    private final TaskApplicationService service;
@Autowired
private final TaskService taskService;
@Autowired
private final TaskApplicationService taskApplicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> applyTask(@RequestBody TaskApplicationDto dto) {
        service.saveApplication(dto);
        return new ResponseEntity<>(new ApiResponse<>(
                201,"Apply task",null
        ),
                HttpStatus.CREATED);

    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<TaskApplicationDto>> getByEmployee(@PathVariable long id) {
        return ResponseEntity.ok(service.getApplicationsByEmployee(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateStatus(@PathVariable long id, @RequestParam String status) {
        service.updateStatus(id, status);
        return new ResponseEntity<>(new ApiResponse<>(
                200,"update task",null
        )
        ,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        service.deleteApplication(id);
        return new ResponseEntity<>(new ApiResponse<>(
             200,"delete Task Apply",null
        ),
                HttpStatus.OK);
    }
    @GetMapping("/all")
    public List<TaskApplicationDto> getAllApplications() {
        return service.getAllApplications();
    }
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getAllTasks() {
        List<TaskDto> allTasks = taskService.getAllTasks();
        return new ResponseEntity<>(new ApiResponse<>(
                200, "Success", allTasks
        ), HttpStatus.OK);
    }
    @GetMapping("/{owner_id}")
    public ResponseEntity<ApiResponse<List<TaskApplicationDto>>> getAllTasksByUserId(@PathVariable Long owner_id) {


        List<TaskApplicationDto> applicationList = taskApplicationService.getAllTasksByOwnerId(owner_id);


        return new ResponseEntity<>(new ApiResponse<>(
                200,
                "Applications fetched successfully",
                applicationList
        ), HttpStatus.OK);
    }

}