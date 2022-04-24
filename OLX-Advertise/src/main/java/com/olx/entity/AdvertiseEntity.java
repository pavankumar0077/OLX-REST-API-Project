package com.olx.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADVERTISE")
public class AdvertiseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String title;
	private String description;
	private double price;
//	private int category;
	private String category;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@Column(name = "modified_date")
	private LocalDate modifiedDate;

	@Column(name = "username")
	private String username;

//	@Column(name = "status_Id")
//	private int statusId;

	@Column(name = "status_Name")
	private String status;

}
