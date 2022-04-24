package com.zensar.actuator;

import java.util.Random;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthActuator extends AbstractHealthIndicator{

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		Random random = new Random();
		int randomNo = random.nextInt(100);
		if(randomNo%2==0)
			builder.up();
		else
			builder.down();
		
	}
	

}
