package com.zensar.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import com.zensar.dto.User;

public interface LoginService {
	
	public String authenticate(User user);

	public boolean logout(String authToken);

//	public ResponseEntity<User> registerUs(User user);
	
	public User registerUser(User user);

	public User getUserById(int id);
	
	public List getUser(String authToken);
	

	public boolean validateToken(String authToken);

//	public User getUser();



}