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
    //Observe malaysia
    //2. addd urls to properties
    //[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
    //[FEAT] Add Indeed categories
    //[FEAT] Add liquibase
    //[REFACTOR] Optimize nofluffjobs and justjoin
    //[FEAT] Timeout per request
    //[FEAT] Selenium localhost/Dockerhub/Diff ports || login/proxy
    //[REFACTOR] Change Security groups on aws
    //[REFACTOR] Change Mapper to SQL Projection
    //[FEAT] Add Glassdor

    //[FRONTEND] TODO
    //[FEAT] Add countries column in technology view
    //[FEAT] Add countries hints in technology input(replace all cities with poland)
    //[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup( ???? )
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[OPTIMIZATION] Responsive Web Design
    //[OPTIMIZATION] Update only changing value in redux
}
