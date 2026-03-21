package edu.lk.ijse.back_end.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private Long id;
    @NotBlank()
    private String title;
    private double budget;
    @NotBlank()
    private String locationName;
    private double latitude;
    private double longitude;
    @NotBlank()
    private String status;
}