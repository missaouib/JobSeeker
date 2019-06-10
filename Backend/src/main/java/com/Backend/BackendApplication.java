package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[CURRENT SPRINT]
	//[FEAT] add input front to db
	//[FEAT] dont save to db the same requests at the same day
	//[CHORE] check if init database is not duplicating at starting point
	//[CHORE] consider removing pk from relational tables
	//[REFACTOR] separate table for date
	//[FIX] fix no polish signs to DB functionality
	//[FIX] psql

	//[BACKEND] TODO
	//[FIX] devtools
	//[FEAT] deploy v1.0
	//[FEAT] add auto play requests script
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] implement apartments scraping
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] add All Jobs placeholder
	//[FEAT] add store
	//[FEAT] About tab
	//[FEAT] history tab and diagrams
}
