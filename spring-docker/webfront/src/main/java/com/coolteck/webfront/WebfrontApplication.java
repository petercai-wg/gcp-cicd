package com.coolteck.webfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfrontApplication {

    public static void main(String[] args) {
        System.out.println("Starting WebfrontApplication...");
        SpringApplication.run(WebfrontApplication.class, args);
    }

}
