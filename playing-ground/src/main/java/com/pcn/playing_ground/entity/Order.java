package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime orderDate;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;
    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;

    @OneToMany(mappedBy = "order")
    private List<OrderAssignment> orderAssignments;

    @OneToMany(mappedBy = "order")
    private List<OrderPromotion> orderPromotions;
}
