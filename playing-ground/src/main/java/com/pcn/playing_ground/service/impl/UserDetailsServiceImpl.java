package com.pcn.playing_ground.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.repository.UserRepo;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepo userRepo;
		
	public UserDetailsServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		 return UserDetailsImpl.build(user);
	}

}
