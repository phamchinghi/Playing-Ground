package com.pcn.playing_ground.service.impl;

import com.pcn.playing_ground.JWT.JwtTokenUtils;
import com.pcn.playing_ground.common.ErrorCode;
import com.pcn.playing_ground.common.exceptions.FieldNotBlankException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.request.LoginRequest;
import com.pcn.playing_ground.dto.request.RefreshTokenRequest;
import com.pcn.playing_ground.dto.request.SignupRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.dto.response.JwtResponse;
import com.pcn.playing_ground.dto.response.LoginResponse;
import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.factories.RoleFactory;
import com.pcn.playing_ground.service.AuthService;
import com.pcn.playing_ground.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleFactory roleFactory;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignupRequest signUpRequest) throws UserAlreadyExistsException, RoleNotFoundException {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
        }
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }

        User user = createUser(signUpRequest);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.builder()
                        .success(true)
                        .message("Your account has been created successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> login(LoginRequest loginRequest, HttpServletResponse servletResponse) throws FieldNotBlankException {
        UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtils.generateToken(authentication.getName());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);
        List<String> rolesList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String BEARER = "Bearer";
        LoginResponse response = LoginResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .token(jwt)
                .refreshToken(refreshToken)
                .type(BEARER)
                .roles(rolesList)
                .build();
        /*handle refresh token */
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true if allow sends only through HTTPS
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
        servletResponse.addCookie(cookie);

        return ResponseEntity.ok(ApiResponseDto.builder()
                .success(true)
                .message("Sign in successfully!")
                .response(response)
                .build());
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> refreshToken(RefreshTokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();
        String username = jwtTokenUtils.getUsernameFromToken(refreshToken);

        if (username != null && jwtTokenUtils.validateToken(refreshToken)) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtTokenUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(ApiResponseDto.builder()
                    .success(true)
                    .message("Refresh Token Completed!")
                    .response(new JwtResponse(newAccessToken, refreshToken))
                    .build());
        }
        return ResponseEntity.status(ErrorCode.TOKEN_INVALID.getHttpStatus()).body(
                ApiResponseDto.builder()
                        .success(false)
                        .message(ErrorCode.TOKEN_INVALID.getMessage())
                        .build());
    }

    /*Create new user*/
    private User createUser(SignupRequest signUpRequest) throws RoleNotFoundException {
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .passwrd(passwordEncoder.encode(signUpRequest.getPassword()))
                .is_active(true)
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .phone(signUpRequest.getPhone())
                .roles(roleFactory.determineRoles(signUpRequest.getRole()))
                .build();
        user.setUpdateBy(ERole.ADMIN.toString());
        return user;
    }
}
