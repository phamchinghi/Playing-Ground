package com.pcn.playing_ground.controller;

import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OderController {

    private final OrderService orderService;

    @Autowired
    public OderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/manageOrder")
    ResponseEntity<ApiResponseDto<?>> getAllOrder(){
        return orderService.getAllOrder();
    }

}
