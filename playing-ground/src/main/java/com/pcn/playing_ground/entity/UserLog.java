package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** @pre
 *  The model's purpose to save the action that UserController took at the time to track when system error.
 *  such as: (LOGIN, LOGOUT, PASSWORD_RESET, etc.)
 */

@Entity
@Table(name = "USER_LOGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLog extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
    private User user;
    @Column(name = "ACTION", length = 50, nullable = false)
    private String action;

    @Column(name = "DESCRIPTIONS", length = 255)
    private String description;

    @Column(name = "ACTION_TIME", nullable = false)
    private LocalDateTime actionTime;
}
