package com.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OlxEurekaServIceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxEurekaServIceApplication.class, args);
	}

}
