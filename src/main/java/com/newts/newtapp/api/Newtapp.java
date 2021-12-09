package com.newts.newtapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.newts.newtapp.entities")
public class Newtapp {

    public static void main(String[] args) {
        SpringApplication.run(Newtapp.class, args);
    }
}
