package edu.lk.ijse.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String access_token;
    private long id;
    private String name;
}
