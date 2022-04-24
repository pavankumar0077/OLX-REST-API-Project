package com.zensar.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoActuator implements InfoContributor{

	@Override
	public void contribute(Builder builder) {
//		System.out.println("Stock App Info :: ");
		
		Map<String, Integer> serviceDetails = new HashMap<String, Integer>();
		serviceDetails.put("StockApp-service-1", 101);
		serviceDetails.put("StockApp-service-2", 102);
        builder.withDetail("Services", serviceDetails);
		
	}

}
