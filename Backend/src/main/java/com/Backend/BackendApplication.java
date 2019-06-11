package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[FEAT] add All Jobs ON CITIES ??????
	//[FEAT] add special case for poland/all jobs/ all it jobs on DB
	//[FEAT] dont save to db the same requests at the same day
	//[CHORE] check if init database is not duplicating at starting point
	//[CHORE] consider removing pk from relational tables
	//[REFACTOR] separate table for date
	//[FIX] fix no polish signs on DB functionality
	//[FIX] psql
	//[FIX] devtools
	//[FEAT] deploy v1.0
	//[FEAT] add auto play requests script
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] implement apartments scraping
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[REFACTOR] consider autocomplete on technology-input
	//[FEAT] create about tab
	//[FEAT] add store
	//[FEAT] history tab and diagrams
}
