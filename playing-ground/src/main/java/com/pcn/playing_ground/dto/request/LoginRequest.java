package com.pcn.playing_ground.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	@NotBlank(message = "User Name is required!")
	private String username;
	@NotBlank(message = "Email is required!")
	private String password;
}
