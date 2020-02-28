package com.westjet.middleware.roomservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.westjet.middleware.roomservices")
@EnableAspectJAutoProxy
public class RoomServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomServicesApplication.class, args);
	}

}
