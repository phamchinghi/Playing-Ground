package com.pcn.playing_ground.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	@NotBlank(message = "username is required!")
	private String username;
	@NotBlank(message = "password is required!")
	private String password;
}
