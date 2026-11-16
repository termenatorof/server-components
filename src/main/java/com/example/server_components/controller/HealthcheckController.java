package com.example.server_components.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthcheckController {

    @GetMapping("/healthz/live")
    public Map livenessCheck() {
        return Map.of(
                "status", "alive",
                "timestamp", Instant.now().toString()
        );
    }

    @GetMapping("/healthz/ready")
    public Map readinessCheck() {
        return Map.of(
                "status", "ready",
                "database", "not_connected_yet",
                "timestamp", Instant.now().toString()
        );
    }
}