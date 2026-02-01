package com.bintang.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootUserManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootUserManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUserManagementApplication.class, args);
		log.info("SERVER IS RUNNING");
	}

}
