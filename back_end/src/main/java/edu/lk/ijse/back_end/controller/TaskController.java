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
    TaskService taskService;
    @PostMapping
    public ResponseEntity<ApiResponse<String>>saveTask(@RequestBody @Valid TaskDto taskDto){
        taskService.saveTask(taskDto);
        return new ResponseEntity<>(new ApiResponse<>(
                201,"Task saved",null
        ),
                HttpStatus.CREATED);


    }
    @PutMapping
    public ResponseEntity<ApiResponse<String>>updateTask(@RequestBody @Valid TaskDto taskDto){
        taskService.updateTask(taskDto);
        return new ResponseEntity<>(new ApiResponse<>(
                200,"Task updated",null
        ),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>deleteTask(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(new ApiResponse<>(
                200,"Task deleted",null
        ),HttpStatus.OK);
    }
    @GetMapping("/all")
    public List<TaskDto> getAllTasks(){
        return taskService.getAllTasks();
    }
}
