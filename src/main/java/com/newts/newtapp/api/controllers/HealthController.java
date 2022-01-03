package com.newts.newtapp.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    /**
     * Return status OK iff this API server is up and running.
     * Implemented for AWS load balancer target status checking, but can be used generally to check server status.
     */
    @GetMapping("/api/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }
}
