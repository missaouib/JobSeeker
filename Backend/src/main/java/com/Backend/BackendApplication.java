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

    /*
    Nested menu(one button) java dateFrom, dateTo
    java series [linkedin offers, date]
    */

    //[BACKEND] TODO
    //[FEAT] Create endpoints and logic for frontend charts
    //[FEAT] Consider nofluff {experience, remote, category}
    //[FEAT] Consider justjoinit {experience, remote, salary}
    //[FEAT] Try Selenium with proxy for Linkedin and Glassdor

    //[FRONTEND] TODO
    // similar pages trends google/stack
    //[FEAT] Add history Line and Bar diagrams (ngx-charts)
    //[FEAT] Responsive Web Design
}
