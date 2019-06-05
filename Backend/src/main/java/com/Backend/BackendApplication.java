package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[FEAT] consider linkedin categories
	//[REFACTOR] simplify URLs and fix keywords(cpp, small cities, polish linkedin)
	//[REFACTOR] LocalDate to server side and other tables
	//[FEAT] database, init methods
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] implements apartments scraping
	//[FEAT] add auto play requests script
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] add diagrams
}
