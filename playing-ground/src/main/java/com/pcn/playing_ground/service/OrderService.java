package com.pcn.playing_ground.service;

import com.pcn.playing_ground.dto.response.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity<ApiResponseDto<?>> getAllOrder();
}
