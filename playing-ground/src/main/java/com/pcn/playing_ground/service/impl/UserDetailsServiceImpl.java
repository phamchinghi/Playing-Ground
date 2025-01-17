package com.pcn.playing_ground.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<User> userOpt = userRepo.findByUsername(username);
	        
        if (userOpt.isPresent()) {
        	User user = userOpt.get();
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPasswrd(),
                    mapRolesToAuthorities(user.getRoles()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
	}
	
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles){
		Collection < ? extends GrantedAuthority> mapRoles;
		
		mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRolename().toString()))
				.collect(Collectors.toList());
		
        return mapRoles;
	}
}
