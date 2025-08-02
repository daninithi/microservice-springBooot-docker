package com.spring_boot.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_boot.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
