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
    //add more break in scheduler
    //UINDEED LAST 30 DAYS XD fromage=30 xdddddddddddddddddddddddddddddddd
    //[FEAT] Create endpoints and logic for frontend charts
    //[FEAT] Consider nofluff {experience, remote, category}
    //[FEAT] Consider justjoinit {experience, remote, salary}
    //[FEAT] Try Selenium with proxy for Linkedin and Glassdor

    //[FRONTEND] TODO
    // if 0 then hide ?
    // think about jquery etc.
    //[REFACTOR] Add indeed (Category - new table)
    //[FIX] Counting ids in World tab view
    //[FEAT] Add history diagram functionality
    //[FEAT] Responsive Web Design
}
