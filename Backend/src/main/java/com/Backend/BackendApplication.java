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
    //[FEAT] Add temporary technology Country + manual test
    //[FEAT] Add Liquibase
    //[FEAT] Create endpoints and logic for frontend charts
    //[FEAT] Consider nofluff {experience, remote, category}
    //[FEAT] Consider justjoinit {experience, remote, salary}
    //[FEAT] Try Selenium with proxy for Linkedin and Glassdor

    //[FRONTEND] TODO
    //[REFACTOR] remove "all ..." functionality
    //[REFACTOR] Check ids from DTO
    //[FEAT] Add countries column in technology view
    //[FEAT] Add countries hints in technology input(replace all cities with poland)
    //[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup( ???? )
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[FEAT] Responsive Web Design
}
