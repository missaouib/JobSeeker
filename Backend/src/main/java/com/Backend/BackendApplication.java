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
	//[FIX] Make selenium much more faster
	//[FEAT] add glassdor & indeed
	//[FEAT] add 'All World' functionality in Technology stats
	//[FEAT] Consider adding green/red indexing arrows
	//[REFACTOR] get methods & modelmapper converter
	//[FEAT] nofluff/justjoin {category, experience} + {salary}
	//[FEAT] add json web token
	//[TEST] add tests

	//[FRONTEND] TODO
	//[FEAT] add history diagram functionality
	//[OPTIMIZATION] responsive table
	//[OPTIMIZATION] update only changing value in store
}
