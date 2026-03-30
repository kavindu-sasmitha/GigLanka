package edu.lk.ijse.back_end.exception;

import edu.lk.ijse.back_end.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. සාමාන්‍යයෙන් සිදුවන ඕනෑම error එකක් සඳහා (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception e) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 2. Resource එකක් සොයාගත නොහැකි වූ විට (Not Found)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // 3. දැනටමත් පවතින දත්ත නැවත ඇතුළත් කිරීමට යාමේදී (Conflict)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                "Resource Already Exists", e.getMessage()),
                HttpStatus.CONFLICT);
    }

    // 4. Bean Validation (Validation Failed)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors),
                HttpStatus.BAD_REQUEST);
    }
}
