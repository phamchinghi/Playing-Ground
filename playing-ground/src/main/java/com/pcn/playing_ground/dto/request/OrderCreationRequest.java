package com.pcn.playing_ground.dto.request;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreationRequest implements Serializable {
    List<OrderDetailRequest> orderDetailRequests;

    @Data
    public static class OrderDetailRequest implements Serializable{
        private Long productID;
        private Integer quantity;
        private BigDecimal itemDiscount;
        private BigDecimal price;
    }

}
