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
    //separate category requests
    //check all checkbox on startup

    //[BACKEND] TODO
    //[FEAT] Consider nofluff {experience, remote, category}
    //[FEAT] Consider justjoinit {experience, remote, salary}
    //[FEAT] Try Selenium with proxy for Linkedin and Glassdor

    //[FRONTEND] TODO
    //[FEAT] Responsive Web Design
}
