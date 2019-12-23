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
    //[FEAT] Add Indeed categories only in poland
    //[FEAT] Check if scheduler added all data
    //[FIX] if poland == all cities
    //[REFACTOR] Optimize nofluffjobs and justjoin
    //[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
    //[FEAT] Add liquibase
    //[FEAT] Try Selenium for Linkedin and Glassdor

    //[FRONTEND] TODO
    //[REFACTOR] Check ids from DTO
    //[FEAT] Add countries column in technology view
    //[FEAT] Add countries hints in technology input(replace all cities with poland)
    //[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup( ???? )
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[FEAT] Responsive Web Design
}
