package com.pcn.playing_ground.controller;

import com.pcn.playing_ground.common.exceptions.AppException;
import com.pcn.playing_ground.common.exceptions.FieldNotBlankException;
import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.dto.request.UserUpdateRequest;
import com.pcn.playing_ground.dto.response.ApiResponseDto;
import com.pcn.playing_ground.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserController {
    private final UserService userService;
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDto<?>> getUser(@PathVariable Long id) throws AppException, RoleNotFoundException {
        return userService.getUserDetailByUserLogin(id);
    }

    @PutMapping("/update")
    ResponseEntity<ApiResponseDto<?>> updateUser(@RequestBody @Valid UserUpdateRequest updateRequest) throws AppException, FieldNotBlankException {
       return userService.updateUser(updateRequest);
    }
}
