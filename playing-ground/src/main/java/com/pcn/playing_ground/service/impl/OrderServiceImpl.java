package com.pcn.playing_ground.service.impl;

import com.pcn.playing_ground.common.ErrorCode;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.dto.response.OrderCreationResponse;
import com.pcn.playing_ground.entity.Order;
import com.pcn.playing_ground.repository.OrderRepo;
import com.pcn.playing_ground.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepo orderRepo;
    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllOrder() {
        LOGGER.info("Get all user");
        List<Object[]> listOrder = orderRepo.findOrdersWithUserFirstName();
        if (listOrder.isEmpty()) {
            return ResponseEntity.status(ErrorCode.ORDER_NOT_FOUND.getCode()).body(
                ApiResponseDto.builder()
                    .success(false)
                    .message(ErrorCode.ORDER_NOT_FOUND.getMessage())
                    .build());
        }
        List<OrderCreationResponse> listResponse = new ArrayList<>();
        for (Object[] object : listOrder) {
            String firstName = String.valueOf(object[0]);
            Order order = (Order) object[1];
            OrderCreationResponse response = OrderCreationResponse.builder()
                    .orderID(order.getId())
                    .ownerName(firstName)
                    .orderDate(order.getOrderDate().toLocalDate())
                    .totalAmount(order.getTotalAmount().doubleValue())
                    .discount(order.getDiscount().doubleValue())
                    .build();
            listResponse.add(response);
        }
        return ResponseEntity.ok(ApiResponseDto.builder()
                .success(true)
                .message("Get Order successfully!")
                .response(listResponse)
                .build());
    }
}
