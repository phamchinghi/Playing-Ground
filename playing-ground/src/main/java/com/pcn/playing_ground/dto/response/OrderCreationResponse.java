package com.pcn.playing_ground.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderCreationResponse implements Serializable {
    private Long orderID;
    private String ownerName;
    private Long userID;
    private LocalDate orderDate;
    private Double totalAmount;
    private Double discount;
    private List<OrderDetailResponse> orderDetailResponses;

    @Data
    @Builder
    public static class OrderDetailResponse{
        private Long productID;
        private Integer quantity;
        private BigDecimal itemDiscount;
        private BigDecimal price;
    }
}
