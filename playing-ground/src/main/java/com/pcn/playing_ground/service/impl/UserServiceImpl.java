package com.pcn.playing_ground.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pcn.playing_ground.JWT.JwtTokenUtils;
import com.pcn.playing_ground.common.ErrorCode;
import com.pcn.playing_ground.common.ExceptionHandling;
import com.pcn.playing_ground.common.exceptions.AppException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.dto.response.UserDetailResponse;
import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.factories.RoleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.UserRepo;
import com.pcn.playing_ground.service.UserService;

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
                .map(role -> new String(role.getRolename().name()))
                .collect(Collectors.toList());
        UserDetailResponse response = UserDetailResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .passwrd(user.getPasswrd())
                .is_active(user.is_active())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .dattime(user.getDattime())
                .update_by(user.getUpdate_by())
                .roles(userRole)
                .build();
        return ResponseEntity.ok(ApiResponseDto.builder()
                .success(true)
                .message("Get User successfully!")
                .response(response)
                .build());
    }

}
