package com.epam.malykhin.service;

import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.UserDAO;
import com.epam.malykhin.database.dao.mysql.MySqlUser;
import com.epam.malykhin.database.entity.User;

import javax.servlet.ServletContext;

import static com.epam.malykhin.util.StaticTransformVariable.CONTEXT_LISTENER_TRANSACTION_MANAGER;
import static com.epam.malykhin.util.StaticTransformVariable.USER_DAO;

/**
 * Created by Serhii_Malykhin on 12/2/2016.
 */
public class UserService implements Service {
    private UserDAO userDAO;
    private TransactionManager transactionManager;

    public void init(ServletContext context) {
        userDAO = (MySqlUser) context.getAttribute(USER_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public User selectUserByEmail(User user) {
        return (User) transactionManager.execute(connection -> userDAO.selectUserByEmail(connection, user));
    }

    public boolean insert(User user) {
        return (Boolean) transactionManager.execute(connection -> userDAO.insert(connection, user));
    }

    public User select(User user) {
        return (User) transactionManager.execute(connection -> userDAO.select(connection, user));
    }
}