package com.zensar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.User;
import com.zensar.exception.InvalidAuthTokenException;
import com.zensar.exception.InvalidCredentialsException;
import com.zensar.service.LoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx/login")
@CrossOrigin(origins = "*")
public class LoginController {
	@Autowired
	LoginService loginService;

	@ExceptionHandler(value = InvalidCredentialsException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionInvalidCredentials(InvalidCredentialsException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = InvalidAuthTokenException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionAuthToken(InvalidAuthTokenException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.FORBIDDEN);
	}

	// 1
	@PostMapping(value = "/user/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "user Auth", notes = "authenticating a user using tokens")
	public ResponseEntity<String> authenticate(@RequestBody User user) {
		return new ResponseEntity<String>(loginService.authenticate(user), HttpStatus.OK);
	}

	// 2
	@DeleteMapping(value = "/user/logout")
	@ApiOperation(value = "logs out  a user", notes = "logs out a user session")
	public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Boolean>(loginService.logout(authToken), HttpStatus.OK);
	}

	// 3
	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "create a new user", notes = "This REST api post a new user")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		return new ResponseEntity<User>(loginService.registerUser(user), HttpStatus.CREATED);
	}

	// 4
	@ApiOperation(value = "getUser", notes = "return a user")
	@GetMapping(value = "/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
		return new ResponseEntity<User>(loginService.getUserById(id), HttpStatus.OK);
	}

	// 4
	@GetMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Getting a User Information", notes = "This Rest API helps to get Authenticated User Data")
	public ResponseEntity<List> getUser(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<List>(loginService.getUser(authToken), HttpStatus.OK);
	}

	//5
	@GetMapping(value = "/token/validate", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "token Validation", notes = " validates a token ")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Boolean>(loginService.validateToken(authToken), HttpStatus.OK);
	}

}
