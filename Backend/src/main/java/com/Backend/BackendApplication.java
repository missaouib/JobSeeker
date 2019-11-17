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
	//[FEAT] Timeout per request for production?
	//[FEAT] Selenium localhost/Dockerhub/Diff ports
	//[FEAT] Add Indeed
	//[REFACTOR] Change Security groups on aws
	//[REFACTOR] Optimize nofluffjobs scrap by one request
	//[FEAT] Add 'All World' functionality in Technology stats
	//[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
	//[FEAT] Login selenium or proxy
	//[FEAT] Add Glasdor
	//[REFACTOR] Get methods & modelmapper converter

	//[FRONTEND] TODO
	//[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup
	//[FIX] Counting ids in World tab view
	//[FEAT] Add history diagram functionality
	//[OPTIMIZATION] Responsive table
	//[OPTIMIZATION] Update only changing value in store
}
