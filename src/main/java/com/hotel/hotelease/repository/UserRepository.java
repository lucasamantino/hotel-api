package com.hotel.hotelease.repository;

import com.hotel.hotelease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
}
