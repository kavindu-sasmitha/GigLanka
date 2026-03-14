package edu.lk.ijse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String fullName;
    private String userName; // Email
    private String password;
    private String nic;
    private String role; // EMPLOY,TASK_OWNER
}