/*
 * Copyright Jordy Coder (c) 2021
 */

package com.fp.forgotpassword.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.fp.forgotpassword.model.User;
import com.fp.forgotpassword.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;


    private final UserRepository userRepository;

    public String forgotPassword(String email) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));

        if (userOptional.isEmpty()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepository.save(user);

        return user.getToken();
    }

    public String resetPassword(String token, String password) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByToken(token));

        if (userOptional.isEmpty()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }

        User user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepository.save(user);

        return "Your password successfully updated.";
    }
    private String generateToken() {

        return String.valueOf(UUID.randomUUID()) +
                UUID.randomUUID();
    }
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

}
