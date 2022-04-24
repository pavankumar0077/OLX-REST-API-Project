package com.olx.dto;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ADVERTISE DTO")
public class Advertise {

	@ApiModelProperty(value = "Id")
	private int id;

	@ApiModelProperty(value = "Title")
	private String title;
	
	@ApiModelProperty(value = "category_Id")
	private int categoryId;
	
	@ApiModelProperty(value = "Status Id")
	private int statusId;

	@ApiModelProperty(value = "price")
	private double price;

	@ApiModelProperty(value = "description")
	private String description;

	@ApiModelProperty(value = "category_name")
	private String categoryName;

	@ApiModelProperty(value = "createdDate")
	private LocalDate createdDate;

	@ApiModelProperty(value = "modifiedDate")
	private LocalDate modifiedDate;

//	@ApiModelProperty(value = "active")
//	private String active;
	
	
	@ApiModelProperty(value = "Category")
	private String category;

	@ApiModelProperty(value = "username")
	private String username;
	
	
	
	@ApiModelProperty(value = "Status Name")
	private String statusName;

	

	
}
