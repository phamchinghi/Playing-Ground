package com.pcn.playing_ground.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssignmentId implements Serializable {
    private Long orderId;
    private Long userId;
}
