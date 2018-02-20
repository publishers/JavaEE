package com.epam.service;

import com.epam.database.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
//    @Autowired
//    private UserDAO userDAO;
//    @Autowired
//    private TransactionManager transactionManager;

//    public void init(ServletContext context) {
//        userDAO = (MySqlUser) context.getAttribute(USER_DAO);
//        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
//    }

    public User selectUserByEmail(User user) {
//        return (User) transactionManager.execute(connection -> userDAO.findByEmail(connection, user));
//        return userDAO.findByEmail(user.getEmail());
        return null;
    }

    public boolean insert(User user) {
//        return (Boolean) transactionManager.execute(connection -> userDAO.insert(connection, user));
        return false;
    }

    public User select(User user) {
//        return (User) transactionManager.execute(connection -> userDAO.select(connection, user));
        return new User();
    }
}