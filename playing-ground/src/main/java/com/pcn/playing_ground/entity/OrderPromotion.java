package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_PROMOTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPromotion{
    @EmbeddedId
    private OrderPromotionId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "OrderID", referencedColumnName = "ID", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "PromotionID",referencedColumnName = "ID", nullable = false)
    private Promotion promotion;
}
