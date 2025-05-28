package com.arktech.toppickr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ToppickrApplication {
	public static void main(String[] args) {
		SpringApplication.run(ToppickrApplication.class, args);
	}
}
