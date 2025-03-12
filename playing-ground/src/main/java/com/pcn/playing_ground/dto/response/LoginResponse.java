package com.pcn.playing_ground.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Builder
public class LoginResponse {
	private String token;
	private String refreshToken;
	private String type;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
}
