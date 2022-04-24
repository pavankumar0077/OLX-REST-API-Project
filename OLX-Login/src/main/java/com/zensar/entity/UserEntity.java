package com.zensar.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "olxdetails")
public class UserEntity {

	@Id
	@GeneratedValue
	private int id;

	private String firstName;

	private String lastName;

//	@Id
//	@Column(name = "UserName", nullable = false) 
//	@Column(columnDefinition = "text")
	private String userName;

	private String password;

	private String email;

	private double phone;

}
