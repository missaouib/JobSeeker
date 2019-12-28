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
    //no technology nofluff
    // check countries
    // all cities for indeed
    //[REFACTOR] Optimize nofluffjobs and justjoin
    //[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
    //[FEAT] Add liquibase
    //Change the var names
    //[FEAT] Add temporary technology DTO
    //[FEAT] Try Selenium for Linkedin and Glassdor

    //[FRONTEND] TODO
    //check if sending polandto backend
    //[REFACTOR] Check ids from DTO
    //[FEAT] Add countries column in technology view
    //[FEAT] Add countries hints in technology input(replace all cities with poland)
    //[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup( ???? )
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[FEAT] Responsive Web Design
}
