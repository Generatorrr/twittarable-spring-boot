package com.example.twittarablespringboot.repository;

import com.example.twittarablespringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByActivationCode(String code);
}
