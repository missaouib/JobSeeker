package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[OPTIMIZATION] send only one date per request
	//[FEAT] deploy v1.0
	//[FEAT] add auto play requests script
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] handle internal 500?
	//[FEAT] style about view
	//[OPTIMIZATION] responsive table
	//[OPTIMIZATION] update only changing value in store
}
