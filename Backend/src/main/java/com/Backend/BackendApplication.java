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

    //[FEAT] Diagram module
    // change label name due to selected profile
    // criteria api for portals
    // regular endpoints
    // "All" endpoints

    //[BACKEND] TODO
    //[FEAT] Consider nofluff {experience, remote, category}
    //[FEAT] Consider justjoinit {experience, remote, salary}
    //[FEAT] Try Selenium with proxy for Linkedin and Glassdor

    //[FRONTEND] TODO
    //[FEAT] Responsive Web Design
}
