package com.epam.service;

import com.epam.database.dao.UserDAO;
import com.epam.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User selectUserByEmail(User user) {
        throw new UnsupportedOperationException();
    }

    public boolean insert(User user) {
        return false;
    }

    public User select(User user) {
        return new User();
    }
}