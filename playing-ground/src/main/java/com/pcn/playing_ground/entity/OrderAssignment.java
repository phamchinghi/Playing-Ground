package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_ASSIGNMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssignment extends BaseEntity{
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "OrderID", referencedColumnName = "ID", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
    private User user;

    @Column(name = "AssignedDate")
    private LocalDateTime assignedDate;
}
