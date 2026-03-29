package edu.lk.ijse.back_end.entity;

import edu.lk.ijse.back_end.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String full_name;
    private String nic;
    private String district; // New: District based filtering
    private double walletBalance = 0.0; // New: For payments

    @Enumerated(EnumType.STRING)
    private Role role;

    // Security methods (getAuthorities etc. stay same as before)
}