/*
 * Copyright Jordy Coder (c) 2021
 */

package com.fp.forgotpassword.controller;

import com.fp.forgotpassword.service.UserService;
import lombok.AllArgsConstructor;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/forgot-password")
    public <token> String forgotPassword(@RequestParam (required = false) String email) {

        String response = userService.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8080/reset-password?token=" + response;
        }
        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam (required = false)String token,
                                @RequestParam (required = false)String password) {

        return userService.resetPassword(token, password);
    }
    @GetMapping("/users")
    public String getPassword (@RequestParam (required = false) String email){
        return userService.forgotPassword( email);
    }
}
