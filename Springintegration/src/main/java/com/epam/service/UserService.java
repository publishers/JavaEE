package com.epam.service;

import com.epam.database.dao.UserDAO;
import com.epam.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User selectUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public boolean insert(User user) {
        return false;
    }

    public User select(User user) {
        return userDAO.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}