package com.pcn.playing_ground.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcn.playing_ground.dto.RegisterRequest;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.UserRepo;

@Service
public class UserService{
	
	@Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswrd(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        return userRepo.save(user);
    }
    
    public boolean authenticate(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        
        return false;
    }

    public User savePassord(User user) {
        user.setPasswrd(passwordEncoder.encode(user.getPasswrd()));
        return userRepo.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
