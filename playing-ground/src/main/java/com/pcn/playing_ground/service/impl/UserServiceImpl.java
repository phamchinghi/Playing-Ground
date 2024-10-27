package com.pcn.playing_ground.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.UserRepo;
import com.pcn.playing_ground.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public User insertUser(SignupRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswrd(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setActive(true);
        
        return userRepo.save(user);
    }
    
    public boolean authenticate(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPasswrd());
        }
        
        return false;
    }
    
    public User savePassword(User user) {
        user.setPasswrd(passwordEncoder.encode(user.getPasswrd()));
        return userRepo.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    public Boolean existsByUsername(String username) {
    	return userRepo.existsByUsername(username);
    }
    
    public Boolean existsByEmail(String email) {
    	return userRepo.existsByEmail(email);
    }
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
}
