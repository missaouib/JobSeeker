package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[FEAT] docker volumes
	//[REFACTOR] get methods & modelmapper converter
	//[FEAT] deploy v1.0
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] switch to production mode before release
	//[FEAT] add history diagram functionality
	//[OPTIMIZATION] responsive table
	//[OPTIMIZATION] update only changing value in store
}
