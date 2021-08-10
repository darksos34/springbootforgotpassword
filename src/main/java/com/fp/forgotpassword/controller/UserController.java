/*
 * Copyright Jordy Coder (c) 2021
 */

package com.fp.forgotpassword.controller;

import com.fp.forgotpassword.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam (value = "params",required = false) String email) {

        String response = userService.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8080/reset-password?token=" + response;
        }
        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        return userService.resetPassword(token, password);
    }
}
