package com.olx.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.olx.dto.Advertise;

public interface AdvertiseService {

	public Advertise createNewAdvertise(Advertise advertise, String authToken);
	public Advertise updateAdvertise(Advertise Advertise, int id, String authtoken);
	public List<Advertise> getAllAdvertises(String authToken);
	public List<Advertise> getAdvertiseAllById(int id, String authToken);
	
	public Boolean deleteAdvertiseById(int id, String authtoken);
	public List<Advertise> filterAdvertise(String searchText, Integer categoryId, String postedBy,String dateCondition, LocalDate onDate,LocalDate fromDate,
			LocalDate toDate, String sortedBy, int startIndex, int records);
	public List<Advertise> SearchAdvertiseByText(String searchText);

	public Advertise getAdvertiseById(int id, String authToken);
}
