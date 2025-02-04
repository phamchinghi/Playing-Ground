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
import com.pcn.playing_ground.entity.User;
import com.pcn.playing_ground.factories.RoleFactory;
import com.pcn.playing_ground.service.AuthService;
import com.pcn.playing_ground.service.UserService;
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
import java.util.List;

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
                        .message("User account has been successfully created!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> login(LoginRequest loginRequest, HttpServletResponse servletResponse) throws FieldNotBlankException{
        if(loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()){
            throw new FieldNotBlankException("Login failed: Username or Password must be enter");
        }
        try {
            UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenUtils.generateToken(authentication.getName());

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> rolesList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            String BEARER = "Bearer";
            LoginResponse response = LoginResponse.builder()
                    .id(userDetails.getId())
                    .username(userDetails.getUsername())
                    .email(userDetails.getEmail())
                    .token(jwt)
                    .type(BEARER)
                    .roles(rolesList)
                    .build();
/*handle refresh token later*/
//            Cookie cookie = new Cookie("refreshToken", refreshToken);
//            cookie.setHttpOnly(true);
//            cookie.setSecure(false); // true if allow sends only through HTTPS
//            cookie.setDomain("localhost");
//            cookie.setPath("/");
//            cookie.setMaxAge(14 * 24 * 60 * 60); // 2 weeks
//            servletResponse.addCookie(cookie);

            return ResponseEntity.ok(ApiResponseDto.builder()
                    .success(true)
                    .message("Sign in successfully!")
                    .response(response)
                    .build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponseDto.builder()
                            .success(false)
                            .message("Invalid username or password")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseDto.builder()
                            .success(false)
                            .message("An unexpected error occurred")
                            .build());
        }
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
                .roles(roleFactory.determineRoles(signUpRequest.getRole()))
                .build();
    }
}
