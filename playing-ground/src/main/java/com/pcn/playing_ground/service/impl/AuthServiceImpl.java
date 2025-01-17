package com.pcn.playing_ground.service.impl;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.factories.RoleFactory;
import com.pcn.playing_ground.service.AuthService;
import com.pcn.playing_ground.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleFactory roleFactory;

    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignupRequest signUpRequest) throws UserAlreadyExistsException, RoleNotFoundException {
//        if (userService.existsByEmail(signUpRequest.getEmail())) {
//            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
//        }
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }

        User user = createUser(signUpRequest);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("User account has been successfully created!")
                        .build()
        );
    }

    /*Create new user*/
    private User createUser(SignupRequest signUpRequest) throws RoleNotFoundException {
        return User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .passwrd(passwordEncoder.encode(signUpRequest.getPassword()))
                .is_active(true)
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .phone(signUpRequest.getPhone())
                .dattime(LocalDate.now())
                .update_by(ERole.ADMIN.toString())
                .roles(determineRoles(signUpRequest.getRole()))
                .build();
    }
    private Set<Role> determineRoles(Set<String> strRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            roles.add(roleFactory.getInstance("USER"));
        } else {
            for (String role : strRoles) {
                roles.add(roleFactory.getInstance(role));
            }
        }
        return roles;
    }


}
