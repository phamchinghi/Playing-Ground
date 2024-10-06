package com.pcn.playing_ground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcn.playing_ground.config.AppConstants;
import com.pcn.playing_ground.service.impl.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class Login {

	@Autowired
    private UserService userService;
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("message", "Chào mừng!");
		return  AppConstants.LOGIN;
	}
	
	@PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model, 
                               HttpSession session) {
        if (userService.authenticate(username, password)) {
            session.setAttribute("username", username);
            return AppConstants.REDIRECT_HOME;
        } else {
            model.addAttribute("error", "Invalid username or password");
            return AppConstants.LOGIN;
        }
    }
	
}
