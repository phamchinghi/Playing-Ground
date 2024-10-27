package com.pcn.playing_ground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pcn.playing_ground.config.AppConstants;
import com.pcn.playing_ground.dto.request.LoginRequest;

@Controller
//@RequestMapping("/api/auth")
public class Login {

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("message", "Chào mừng!");
		return  AppConstants.LOGIN;
	}
	
//	@PostMapping("/login")
//    public ResponseEntity<String> processLogin(@RequestBody LoginRequest request) {
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//				request.getUsername(), request.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
	
}
