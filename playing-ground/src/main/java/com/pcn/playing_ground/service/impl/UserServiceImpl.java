package com.pcn.playing_ground.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.UserRepo;
import com.pcn.playing_ground.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Override
    public Boolean existsByUsername(String username) {
    	return userRepo.existsByUsername(username);
    }
    
    @Override
    public Boolean existsByEmail(String email) {
    	return userRepo.existsByEmail(email);
    }
	
    @Transactional
    @Override
	public User save(User user) {
		return userRepo.save(user);
	}
	
}
