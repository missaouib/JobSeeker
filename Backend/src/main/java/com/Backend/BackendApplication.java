package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[REFACTOR] separate table for date?
	//[FEAT] add special case for poland -> all cities?/all jobs/ all it jobs on DB
	//[FEAT] deploy v1.0
	//[FEAT] add auto play requests script
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] fill about view with all the tooltips
	//[FEAT] add store
}
