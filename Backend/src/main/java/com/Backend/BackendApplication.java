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
	//[FEAT] Add Indeed countries
	//[FEAT] Add 'All World' functionality in Technology stats //in polish one more shot jj??
	//[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
	//[FEAT] Add Indeed category
	//[FEAT] Add liquibase
	//[REFACTOR] Optimize nofluffjobs scrap by one request
	//[FEAT] Timeout per request for production?
	//[FEAT] Selenium localhost/Dockerhub/Diff ports
	//[REFACTOR] Change Security groups on aws
	//[FEAT] Login selenium or proxy
	//[FEAT] Add Glasdor
	//[REFACTOR] Change all Posts to Gets xD
	//[REFACOTR] Change substrings to jsoup
	//[REFACTOR] Remove modelmapper and put logic into SQL

	//[FRONTEND] TODO
	//[FEAT] Add countries in technology view
	//[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup
	//[FIX] Counting ids in World tab view
	//[FEAT] Add history diagram functionality
	//[OPTIMIZATION] Responsive table
	//[OPTIMIZATION] Update only changing value in store
}
