package com.example.pr24.dao;

import com.example.pr24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
