package com.pfe.pfeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameTelegram(String usernameTelegram);
    boolean existsByUsernameTelegram(String usernameTelegram);
}