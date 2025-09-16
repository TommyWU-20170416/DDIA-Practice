package com.example.ch1avro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import user.avro.User;

@RestController
public class UserController {

    @GetMapping("/user")
    public User getUser() {
        User user = new User("Tommy", 42, "blue");
        return user;
    }
}
