package edu.lk.ijse.back_end.service;

import edu.lk.ijse.back_end.dto.AuthDto;
import edu.lk.ijse.back_end.dto.AuthResponseDto;
import edu.lk.ijse.back_end.dto.RegisterDto;
import edu.lk.ijse.back_end.entity.enums.Role;
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
        // 1. Spring Security හරහා Authentication එක සිදු කිරීම
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getUserName(),
                        authDTO.getPassword()
                )
        );

        // 2. User ව Database එකෙන් සොයා ගැනීම
        User user = userRepository.findByUsername(authDTO.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. මෙන්න මෙතනට user.getRole().name() එක එකතු කරන්න (Error එක එන්නේ මෙතනටයි)
        // user.getRole() Enum එකක් නම් .name() පාවිච්චි කරන්න, String එකක් නම් කෙලින්ම දෙන්න.
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return new AuthResponseDto(token, user.getId());
    }
}