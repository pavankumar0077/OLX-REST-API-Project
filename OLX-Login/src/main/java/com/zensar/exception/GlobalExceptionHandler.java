package com.zensar.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = SignatureException.class)
	public ResponseEntity<Object> handleInvalidToken(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.toString(), new HttpHeaders(), HttpStatus.OK, request);
	}
	
	@ExceptionHandler(value = InvalidCredentialsException.class)
	public ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
		String errorMessage = "{\"error\": \" "+ exception.toString() + "\"}";
		ResponseEntity<Object> response = 
				handleExceptionInternal(exception, errorMessage, new HttpHeaders(), HttpStatus.CONFLICT, request);
		return response;
		
	}
	
	@ExceptionHandler(value = InvalidAuthTokenException.class)
	public ResponseEntity<Object> handleArithemticError(RuntimeException exception, WebRequest request) {
		String errorMessage = "{\"error\": \" " + exception.toString() + "\"}";
		ResponseEntity<Object> response = 
				handleExceptionInternal(exception, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		return response;
		
	}

}
