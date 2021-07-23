/*
 * Copyright Jordy Coder (c) 2021
 */

package com.fp.forgotpassword.repository;

import com.fp.forgotpassword.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByToken(String token);
}
