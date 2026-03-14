package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.AuthDto;
import edu.lk.ijse.back_end.dto.AuthResponseDto;
import edu.lk.ijse.back_end.dto.RegisterDto;
import edu.lk.ijse.back_end.entity.Role;
import edu.lk.ijse.back_end.entity.User;
import edu.lk.ijse.back_end.repo.UserRepo;
import edu.lk.ijse.back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String saveUser(RegisterDto registerDTO){
        if (userRepository.findByUsername(registerDTO.getUserName()).isPresent()){
            throw new RuntimeException("Email/Username is already in use");
        }

        User user = User.builder()
                .fullName(registerDTO.getFullName())
                .username(registerDTO.getUserName())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .nic(registerDTO.getNic())
                .role(Role.valueOf(registerDTO.getRole().toUpperCase()))
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponseDto authenticate(AuthDto authDTO){
        // Spring Security හරහා Authentication එක සිදු කිරීම
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getUserName(),
                        authDTO.getPassword()
                )
        );

        User user = userRepository.findByUsername(authDTO.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDto(token);
    }
}