package com.zensar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zensar.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	UserEntity findByFirstName(String authToken);

	List<UserEntity> findByUserName(String authToken);

//	List<UserEntity> findByUserName(String username);
	
	
	

}
