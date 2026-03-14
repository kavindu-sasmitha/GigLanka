package edu.lk.ijse.back_end.controller;

import edu.lk.ijse.back_end.dto.ApiResponse;
import edu.lk.ijse.back_end.dto.AuthDto;
import edu.lk.ijse.back_end.dto.RegisterDto;
import edu.lk.ijse.back_end.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("signup")
    public ResponseEntity<ApiResponse>saveUser(@RequestBody RegisterDto registerDTO){
        return ResponseEntity.ok(new ApiResponse(
                200,"User registered successfully",userService.saveUser(registerDTO)));

    }
    @PostMapping("signin")
    public ResponseEntity<ApiResponse>loginUser(@RequestBody AuthDto authDTO){
        return ResponseEntity.ok(new ApiResponse(
                200,"User registered successfully",userService.authenticate(authDTO)));

    }
}