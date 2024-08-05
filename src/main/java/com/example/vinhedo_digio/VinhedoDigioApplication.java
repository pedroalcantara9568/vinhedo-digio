package com.example.vinhedo_digio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VinhedoDigioApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinhedoDigioApplication.class, args);
	}

}
