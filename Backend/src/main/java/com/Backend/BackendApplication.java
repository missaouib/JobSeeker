package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//[BACKEND] TODO
	//[REFACTOR] simplify URLs and fix keywords
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[REFACTOR] LocalDate to server side and other tables
	//[FEAT] database, init methods
	//[FEAT] implements apartments scraping
	//[FEAT] jwt python
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] add diagrams
}
