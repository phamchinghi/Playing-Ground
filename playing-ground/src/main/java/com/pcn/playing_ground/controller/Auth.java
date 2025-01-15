package com.pcn.playing_ground.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	private final RoleRepo roleRepo;
	private final PasswordEncoder encoder;	
	
	@Autowired
	public Auth(AuthenticationManager authenticationManager, UserService userService, RoleRepo roleRepo,
			PasswordEncoder encoder, JwtTokenUtils jwtTokenUtils) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.roleRepo = roleRepo;
		this.encoder = encoder;
		this.jwtTokenUtils = jwtTokenUtils;
	}


	@PostMapping("/register/save")
    public String registerUser(@ModelAttribute("registerRequest") @Valid SignupRequest request,
            BindingResult result,
            Model model){
		if(request.getFirstname().isEmpty()) {
			result.rejectValue("username", null, "First Name do not empty");
		}
		if(request.getLastname().isEmpty()) {
			result.rejectValue("lastname", null, "Last Name do not empty");
		}
        // add check for username exists in a DB
        if(userService.existsByUsername(request.getUsername()) && request.getUsername().trim().isEmpty()){
        	result.rejectValue("username", null,
                    "User name: " + request.getUsername() + " aready existing");
        }
        // add check for email exists in DB
        if(userService.existsByEmail(request.getEmail()) && request.getEmail().trim().isEmpty()){
        	result.rejectValue("email", null,
                    "Email: " + request.getEmail() + " already existing");
        }
        if(request.getPassword().trim().isEmpty()) {
        	result.rejectValue("password", null,
                    "Password do not empty");
        }
        if(!request.getPassword().trim().equals(request.getConfirmPassword().trim())) {
        	result.rejectValue("password", null,
                    "Password does not match");
        }
        if(result.hasErrors()){
        	model.addAttribute("registerRequest", request);
    		return AppConstants.REGISTER;
        }
        // create user object
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswrd(encoder.encode(request.getPassword()));
        user.setUpdateby("admin");
        user.setActive(true);
        user.setDatime(LocalDate.now());
        Role roles = roleRepo.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role ADMIN not found"));;
        user.setRoles(Collections.singleton(roles));
        userService.save(user);
        LOGGER.info("Received request: " + request);
        LOGGER.info("BindingResult: " + result);
        return AppConstants.REGISTER_SUCCESS;
    }
	
	
	@PostMapping("/login")
    public ResponseEntity<?> processLogin(@RequestBody LoginRequest request) {
		try {
			UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenToken);
			String jwt = jwtTokenUtils.generateToken(authentication.getName());
			Map<String, String> response = new HashMap<>();
			response.put("token", jwt);
			response.put("message", "Login successful");

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return ResponseEntity.ofNullable(e);
		}
    }
}
