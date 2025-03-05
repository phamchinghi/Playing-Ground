package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "OrderID", referencedColumnName = "ID", nullable = false)
    private Order order;

    @Column(name = "PaymentDate", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "AmountPaid", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "PaymentMethod", length = 50, nullable = false)
    private String paymentMethod;
}
