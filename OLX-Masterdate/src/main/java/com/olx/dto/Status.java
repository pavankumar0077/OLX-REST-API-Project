package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ApiModel(value = "Status DTO")
public class Status {
	
	@ApiModelProperty(value = "Status Id")
	private int id;
	
	@ApiModelProperty(value = " Status ")
	private String statusName;
}