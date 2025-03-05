package com.pcn.playing_ground.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PROMOTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion extends BaseEntity{
    @Column(name = "PROMOTION_CODE", length = 50, nullable = false, unique = true)
    private String promotionCode;

    @Column(name = "DESCRIPTIONS", length = 255)
    private String description;

    @Column(name = "DISCOUNT_AMOUNT", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "STARTDATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "promotion")
    private List<OrderPromotion> orderPromotions;
}
