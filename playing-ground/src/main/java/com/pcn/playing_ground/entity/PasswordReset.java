package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD_RESETS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
    private User user;

    @Column(name = "RESET_TOKEN", unique = true, nullable = false)
    private String resetToken;

    @Column(name = "REQUEST_TIME", nullable = false)
    private LocalDateTime requestTime;

    @Column(name = "EXPIRE_TIME", nullable = false)
    private LocalDateTime expireTime;

    @Column(name = "IS_USED")
    private Boolean isUsed;
}
