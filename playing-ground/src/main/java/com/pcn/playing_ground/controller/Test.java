package com.pcn.playing_ground.controller;

import com.pcn.playing_ground.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth/test")
public class Test {
    @GetMapping("/home")
    public ResponseEntity<ApiResponseDto<?>> Homepage() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .success(true)
                        .message("HOME page!")
                        .build());
    }

    //    Only users with 'USER' role can access this end point
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<?>> UserDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .success(true)
                        .message("User dashboard!")
                        .build());
    }

    //    Only users with 'ADMIN' role can access this end point'
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponseDto<?>> AdminDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .success(true)
                        .message("Admin dashboard!")
                        .build());
    }

//    //    Users with 'USER' or 'ADMIN' roles can access this end point'
//    @GetMapping("/home")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<ApiResponseDto<?>> AdminOrSuperAdminContent() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(ApiResponseDto.builder()
//                        .success(true)
//                        .message("User or Admin Content!")
//                        .build());
//    }
}
