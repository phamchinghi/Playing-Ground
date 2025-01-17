package com.pcn.playing_ground.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcn.playing_ground.JWT.JwtTokenUtils;
import com.pcn.playing_ground.config.AppConstants;
import com.pcn.playing_ground.dto.request.LoginRequest;
import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.RoleRepo;
import com.pcn.playing_ground.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class Auth {
	private static Logger LOGGER = LoggerFactory.getLogger(Auth.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
	private final AuthService authService;

	@Autowired
	public Auth(AuthenticationManager authenticationManager, UserService userService, JwtTokenUtils jwtTokenUtils, AuthService authService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtTokenUtils = jwtTokenUtils;
		this.authService = authService;
	}


	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignupRequest request)
			throws RoleNotFoundException, UserAlreadyExistsException {
		return authService.signUpUser(request);
	}

	@PostMapping("/login")
    public ResponseEntity<?> processLogin(@RequestBody LoginRequest request) {
		try {
			Map<String, Object> response = new HashMap<>();
			UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenToken);
			if(!authentication.isAuthenticated()) {
				response.put("message", "Unauthenticated");
			}
//			var userDetail = userService.findByUsername(authentication.getName());
			String jwt = jwtTokenUtils.generateToken(authentication.getName());
			response.put("token", jwt);
//			response.put("user", userDetail);
			response.put("message", "Login successful");

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return ResponseEntity.ofNullable(e);
		}
    }
}
