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
    //[FEAT] Nofluff/justjoin {category, experience, remote} + {salary}
    //[FEAT] Add Indeed categories
    //[FEAT] Add liquibase
    //[REFACTOR] Optimize nofluffjobs scrap by one request
    //[FEAT] Timeout per request for production?
    //[FEAT] Selenium localhost/Dockerhub/Diff ports
    //[REFACTOR] Change Security groups on aws
    //[FEAT] Login selenium or proxy
    //[REFACTOR] Change all Posts to Gets xD
    //[REFACTOR] Remove modelmapper and put logic into SQL
    //[FEAT] Add Glassdor?

    //[FRONTEND] TODO
    //[FEAT] Add countries column in technology view
    //[FEAT] Add countries hints in technology input
    //[FEAT] Technology stats <-> if all rows equals to 0 then hide with information popup
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[OPTIMIZATION] Responsive Web Design
    //[OPTIMIZATION] Update only changing value in redux
}
