package com.pcn.playing_ground.service;

import java.util.Optional;

import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService{
	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
	
	public User save(User user);
}
