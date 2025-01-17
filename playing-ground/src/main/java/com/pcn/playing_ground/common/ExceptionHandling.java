package com.pcn.playing_ground.common;

import java.util.ArrayList;
import java.util.List;

import com.pcn.playing_ground.common.exceptions.FieldNotBlankException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.common.exceptions.UserAlreadyExistsException;
import com.pcn.playing_ground.dto.response.ApiResponseDto;

@ControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(value = RuntimeException.class)
	ResponseEntity<String> handlingException(RuntimeException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity
                .badRequest()
                .body(
                		 ApiResponseDto.builder()
                         .success(false)
                         .message("Registration Failed: Please provide valid data.")
                         .response(errorMessage)
                         .build()
                );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDto.builder()
                                .success(false)
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> RoleNotFoundExceptionHandler(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .success(false)
                                .message(exception.getMessage())
                                .build()
                );
    }
	@ExceptionHandler(value = FieldNotBlankException.class)
	public ResponseEntity<ApiResponseDto<?>> FieldNotBlankException(FieldNotBlankException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDto.builder()
                                .success(false)
                                .message(exception.getMessage())
                                .build()
                );
    }
}
