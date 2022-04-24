package com.zensar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OLX LOGIN DTO")
public class User {

//	@ApiModelProperty(value = "ID")
//	private int id;

	@ApiModelProperty(value = "FirstName")
	private String firstName;

	@ApiModelProperty(value = "UserName")
	private String userName;

	@ApiModelProperty(value = "LastName")
	private String lastName;

	@ApiModelProperty(value = "Password")
	private String password;

	@ApiModelProperty(value = "Email")
	private String email;

	@ApiModelProperty(value = "Phone Number")
	private double phone;

}
