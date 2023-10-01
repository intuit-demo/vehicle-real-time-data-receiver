package com.intuit.demo.vehiclerealtimedatareceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VehicleRealTimeDataReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleRealTimeDataReceiverApplication.class, args);
	}

}
