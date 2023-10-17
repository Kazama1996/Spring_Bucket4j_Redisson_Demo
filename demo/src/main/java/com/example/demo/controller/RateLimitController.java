package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ratelimit")
public class RateLimitController {

    @GetMapping("/private/test")
    public String privateTest() {
        return "private test\n";
    }

    @GetMapping("/public/test")
    public String publicTest() {
        return "public test\n";
    }
}
