package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "OrderID", referencedColumnName = "ID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductID", referencedColumnName = "ID", nullable = false)
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "ITEM_DISCOUNT", precision = 5, scale = 2)
    private BigDecimal itemDiscount;
    @Column(name = "PRICE", precision = 10, scale = 2)
    private BigDecimal price;

}
