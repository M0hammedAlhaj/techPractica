package com.spring.techpractica.Core.Otp.Entity;

import com.spring.techpractica.Core.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "OTPS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    @PrePersist
    public void prePersist() {
        otpCode = String.format("%06d", (int) (Math.random() * 1000000));
        expirationDate = LocalDateTime.now().plusMinutes(5);

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
