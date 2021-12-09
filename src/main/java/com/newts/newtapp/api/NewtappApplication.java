package com.newts.newtapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.newts.newtapp.entities")
public class NewtappApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewtappApplication.class, args);
    }
}
