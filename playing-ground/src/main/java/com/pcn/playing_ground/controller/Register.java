package com.pcn.playing_ground.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pcn.playing_ground.config.AppConstants;
import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.RoleRepo;
import com.pcn.playing_ground.service.UserService;

@Controller
//@RequestMapping("/api/auth")
public class Register {
	private static Logger LOGGER = LoggerFactory.getLogger(Register.class);
	@Autowired
    private UserService userService;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder encoder;	
	
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		SignupRequest registerRequest = new SignupRequest();
		registerRequest.setUsername(AppConstants.SPACE);
		registerRequest.setPassword(AppConstants.SPACE);
		registerRequest.setFirstname(AppConstants.SPACE);
		registerRequest.setLastname(AppConstants.SPACE);
		model.addAttribute("registerRequest", registerRequest);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("Current Authentication: " + auth);
		return  AppConstants.REGISTER;
	}
	
	@PostMapping("/register/save")
    public String registerUser(@ModelAttribute("registerRequest") SignupRequest request,
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
}
