package com.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//todo
	//[REFACTOR] Consider init Categories by scraping request
	//[REFACTOR} Think about how works particularly categories on selected sites
	//[REFACTOR] Change to Polish city names on front-end site
	//[FEAT] add RWD on all views
	//[FEAT] add other countries to technology view
    //[REFACTOR] simplify URLs and fix keywords
	//[REFACTOR] date to server side and other tables
	//[FEAT] database, init methods
	//[FEAT] additional data {salary, experience, calculator}
	//------------------
	//[FEAT] animations
	//[FEAT] diagrams
	//[FEAT] RWD
	//------------------
	//[REFACTOR] stringbuilder
	//[FEAT] jwt python
	//[TEST] add tests
}
