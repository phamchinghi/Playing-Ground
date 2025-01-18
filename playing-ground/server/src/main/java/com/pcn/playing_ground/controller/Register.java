package com.pcn.playing_ground.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcn.playing_ground.config.AppConstants;
import com.pcn.playing_ground.dto.request.SignupRequest;

@Controller
public class Register {
	private static Logger LOGGER = LoggerFactory.getLogger(Register.class);
	
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
	
	
}
