package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException{
	
	private String message;
	public InvalidCredentialsException() {
		this.message = "";
	}
	public InvalidCredentialsException(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Invalid Credentials Exception : " +this.message;
	}
	

}
