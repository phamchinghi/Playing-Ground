package com.pcn.playing_ground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcn.playing_ground.config.AppConstants;

@Controller
public class Login {

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("message", "Chào mừng!");
		return  AppConstants.LOGIN;
	}
		
}
