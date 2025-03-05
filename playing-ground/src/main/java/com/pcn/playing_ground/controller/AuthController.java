package com.pcn.playing_ground.controller;

import com.pcn.playing_ground.common.exceptions.FieldNotBlankException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.request.RefreshTokenRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcn.playing_ground.dto.request.LoginRequest;
import com.pcn.playing_ground.dto.request.SignupRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}


	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody SignupRequest request)
			throws RoleNotFoundException, UserAlreadyExistsException {
		return authService.signUpUser(request);
	}

	@PostMapping(value = "/login")
    public ResponseEntity<?> processLogin(@RequestBody LoginRequest request, HttpServletResponse response) throws FieldNotBlankException {
		return authService.login(request, response);
    }

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}
}
