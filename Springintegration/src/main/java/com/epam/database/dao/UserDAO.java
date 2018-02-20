package com.epam.database.dao;

import com.epam.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
