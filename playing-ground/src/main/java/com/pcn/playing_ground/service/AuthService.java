package com.pcn.playing_ground.service;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignupRequest signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
}
