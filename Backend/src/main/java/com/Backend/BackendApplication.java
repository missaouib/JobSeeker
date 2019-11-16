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
	//[FEAT] Add Indeed
	//[REFACTOR] Change Security groups on aws
	//[REFACTOR] Optimize nofluffjobs scrap by one request
	//[FEAT] Add 'All World' functionality in Technology stats
	//[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
	//[FEAT] Login selenium or proxy
	//[FEAT] Add Glasdor
	//[REFACTOR] Get methods & modelmapper converter

	//[FRONTEND] TODO
	//[FEAT] Add history diagram functionality
	//[OPTIMIZATION] Responsive table
	//[OPTIMIZATION] Update only changing value in store
}
