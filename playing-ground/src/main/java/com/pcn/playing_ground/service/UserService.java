package com.pcn.playing_ground.service;

import com.pcn.playing_ground.common.exceptions.AppException;
import com.pcn.playing_ground.common.exceptions.FieldNotBlankException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.dto.request.UserUpdateRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.dto.response.UserDetailResponse;
import com.pcn.playing_ground.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService{
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
	public User save(User user);
	public ResponseEntity<ApiResponseDto<?>> getUserDetailByUserLogin(Long id) throws AppException, RoleNotFoundException;
	public ResponseEntity<ApiResponseDto<?>> updateUser(UserUpdateRequest userUpdate) throws AppException, FieldNotBlankException;
	public ResponseEntity<ApiResponseDto<?>> getAllUser();
	public ResponseEntity<ApiResponseDto<?>> deleteUser(Long id) throws AppException;
}
