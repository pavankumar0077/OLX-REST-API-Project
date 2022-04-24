package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MasterServiceDelegateImpl implements MasterServiceDelegate {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LoginServiceDelegate loginServiceDelegate;

	@Override
	public String getCategoryDescription(int CateId) {
//		if (loginServiceDelegate.isTokenValid(authToken)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(headers);
			String url = "http://localhost:8002/olx/master/CategoryId/{CategoryId}" + CateId;
			ResponseEntity<String> response = 
					this.restTemplate.getForEntity(url, String.class, CateId);
//					this.restTemplate.exchange("http://localhost:8002/olx/master/CategoryId/{CategoryId}/" + CateId, HttpMethod.GET, entity, String.class);
//					"http://API-GATEWAY/olx/master/CategoryId/" + CateId, HttpMethod.GET, entity, String.class);
			return response.getBody();

//		}
//		return null;
	}

	@Override
	public String getStatusName(int StatusId) {
//		if (loginServiceDelegate.isTokenValid(authToken)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(headers);	
			String url = "http://localhost:8002/olx/master/StatusId/{StatusId}" + StatusId;
			ResponseEntity<String> response = 
					this.restTemplate.getForEntity(url, String.class, StatusId);
//					this.restTemplate.exchange("http://localhost:8001/olx/login/token/validate", HttpMethod.GET, entity, Boolean.class);
//					this.restTemplate.exchange("http://localhost:8002/olx/master/CategoryId/{cateId}", HttpMethod.GET, entity, String.class);
//					"http://API-GATEWAY/olx/materData/StatusId/" + StatusId, HttpMethod.GET, entity, String.class);
			return response.getBody();

//		}
//		return null;
	}

}
