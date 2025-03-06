package com.pcn.playing_ground.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest{
    @NotNull(message = "ID is required!")
    private Long id;
    @NotBlank(message = "username is required!")
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
}
