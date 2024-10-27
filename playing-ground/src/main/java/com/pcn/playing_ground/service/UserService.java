package com.pcn.playing_ground.service;

import java.util.Optional;

import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.User;

public interface UserService{
	public User insertUser(SignupRequest request);

	public boolean authenticate(String username, String password);

	public User savePassword(User user);

	public Optional<User> findByUsername(String username);

	public Optional<User> findByEmail(String email);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
	
	public User save(User user);
}
