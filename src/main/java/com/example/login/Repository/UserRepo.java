package com.example.login.Repository;

import org.springframework.stereotype.Repository;

import com.example.login.Entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
