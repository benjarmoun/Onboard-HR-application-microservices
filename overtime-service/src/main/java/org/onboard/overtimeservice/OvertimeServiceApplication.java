package org.onboard.overtimeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OvertimeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvertimeServiceApplication.class, args);
	}

}
