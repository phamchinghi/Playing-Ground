package com.pcn.playing_ground.service.impl;


import com.pcn.playing_ground.common.ErrorCode;
import com.pcn.playing_ground.common.exceptions.AppException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.dto.request.UserUpdateRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.dto.response.UserDetailResponse;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.factories.RoleFactory;
import com.pcn.playing_ground.repository.UserRepo;
import com.pcn.playing_ground.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleFactory roleFactory;
    @Override
    public Boolean existsByUsername(String username) {
    	return userRepo.existsByUsername(username);
    }
    
    @Override
    public Boolean existsByEmail(String email) {
    	return userRepo.existsByEmail(email);
    }
	
    @Transactional
    @Override
	public User save(User user) {
		return userRepo.save(user);
	}

    @Override
    public ResponseEntity<ApiResponseDto<?>> getUserDetailByUserLogin(Long id) throws AppException, RoleNotFoundException {
        LOGGER.info("Get user detail by login");
        User user = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED.getMessage()));
        List<String> userRole = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList());
        UserDetailResponse response = UserDetailResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .passwrd(user.getPasswrd())
                .is_active(user.is_active())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .dattime(user.getDattime().toLocalDate())
                .update_by(user.getUpdateBy())
                .roles(userRole)
                .build();
        return ResponseEntity.ok(ApiResponseDto.builder()
                .success(true)
                .message("Get User successfully!")
                .response(response)
                .build());
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto<?>> updateUser(UserUpdateRequest userUpdate){
        LOGGER.info("========> Update User:{}", userUpdate.getUsername());
        try {
            User user = userRepo.findById(userUpdate.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED.getMessage()));

            user.setEmail(userUpdate.getEmail());
            user.setFirstname(userUpdate.getFirstname());
            user.setLastname(userUpdate.getLastname());
            user.setPhone(userUpdate.getPhone());
            save(user);
            List<String> userRole = user.getRoles().stream().map(role -> role.getRoleName().name()).collect(Collectors.toList());
            UserDetailResponse response = UserDetailResponse.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .passwrd(user.getPasswrd())
                    .is_active(user.is_active())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .phone(user.getPhone())
                    .roles(userRole)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponseDto.builder()
                    .success(true)
                    .message("Save "+userUpdate.getUsername()+" successfully!")
                    .response(response)
                    .build());
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .success(false)
                            .message("An unexpected error occurred")
                            .build());
        }
    }


}
