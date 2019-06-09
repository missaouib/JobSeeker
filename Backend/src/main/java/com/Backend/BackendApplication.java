package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[CURRENT SPRINT]
	//unify date on backend rest side
	//add input front to db / small polish signs
	//dont save to db the same requests at the same day
	//remove black list countries in python script
	//add all categories on it world jobs
	//check if init database is not duplicating????
	//consider removing pk from relational tables
	//fix devtools
	//run psql

	//[BACKEND] TODO
	//[FEAT] deploy v1.0
	//[FEAT] add auto play requests script
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] implement apartments scraping
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] add store
	//[FEAT] About tab
	//[FEAT] history tab and diagrams
}
