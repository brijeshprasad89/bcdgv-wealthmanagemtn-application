package com.bcgdv.wealthmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WealthmgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(WealthmgmtApplication.class, args);
	}

}
