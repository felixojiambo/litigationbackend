package com.emtech.Litigation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LitigationApplication {
	public static void main(String[] args) {
		SpringApplication.run(LitigationApplication.class, args);
		System.out.println("Software Engineer");
	}
}
