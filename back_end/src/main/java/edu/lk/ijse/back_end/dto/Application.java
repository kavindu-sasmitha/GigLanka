package edu.lk.ijse.back_end.dto;

import edu.lk.ijse.back_end.entity.Task;
import edu.lk.ijse.back_end.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task; //apply karanne mona task ekada kiyala


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employee; //applied karanne mona employed

    private String status; // PENDING, ACCEPTED, REJECTED


}