package com.sparta.streaming_practice.user;


import com.sparta.streaming_practice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}