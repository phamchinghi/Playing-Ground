package com.pcn.playing_ground.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(value = RuntimeException.class)
	ResponseEntity<String> handlingException(RuntimeException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
}
