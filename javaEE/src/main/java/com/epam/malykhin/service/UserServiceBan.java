package com.epam.malykhin.service;

import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.UserBanDAO;
import com.epam.malykhin.database.dao.UserDAO;
import com.epam.malykhin.database.dao.mysql.MySqlUser;
import com.epam.malykhin.database.dao.mysql.MySqlUserBan;
import com.epam.malykhin.database.entity.User;
import com.epam.malykhin.database.entity.UserBan;

import javax.servlet.ServletContext;
import java.sql.Timestamp;

import static com.epam.malykhin.util.StaticTransformVariable.*;
import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 12/30/2016.
 */
public class UserServiceBan implements Service {
    private UserBanDAO userBanDAO;
    private UserDAO userDAO;
    private TransactionManager transactionManager;
    private int attempts = 4;

    public void init(ServletContext context) {
        userDAO = (MySqlUser) context.getAttribute(USER_DAO);
        userBanDAO = (MySqlUserBan) context.getAttribute(USER_BAN_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public boolean isUserBan(User user) {
        boolean result = true;
        User tempUser;
        if (isUserExist(tempUser = getUserByEmail(user))) {
            UserBan userBan = getUserBan(tempUser);
            userBan = isUserBanExist(tempUser, userBan);
            result = checkUserBan(tempUser, userBan, user);
        }
        return result;
    }

    private UserBan isUserBanExist(User tempUser, UserBan userBan) {
        if (isNull(userBan)) {
            userBan = createUserBan(tempUser);
            insertUserBan(userBan);
        }
        return userBan;
    }

    private boolean checkUserBan(User tempUser, UserBan userBan, User user) {
        boolean result;
        if (userBan.getAttempt() < attempts) {
            result = isCorrectUser(tempUser, userBan, user, false);
        } else {
            result = isUserBanTimeOver(tempUser, userBan, user);
        }
        return result;
    }

    private boolean isUserBanTimeOver(User tempUser, UserBan userBan, User user) {
        boolean result;
        if (userBan.getDate().getTime() - System.currentTimeMillis() < 0) {
            result = isCorrectUser(tempUser, userBan, user, true);
        } else {
            result = true;
        }
        return result;
    }

    private boolean isCorrectUser(User tempUser, UserBan userBan, User user, boolean isChangeBanTime) {
        boolean result;
        if (!isNull(selectUser(user))) {
            updateUserBan(createUserBan(tempUser));
            result = false;
        } else {
            userBan = isChangeBanTime ? changeUserBanTime(userBan) : userBan;
            userBan.setAttempt(userBan.getAttempt() + 1);
            updateUserBan(userBan);
            result = true;
        }
        return result;
    }

    private UserBan changeUserBanTime(UserBan user) {
        Timestamp date = user.getDate();
        date.setMinutes(date.getMinutes() + 30);
        user.setDate(date);
        return user;
    }

    private UserBan createUserBan(User user) {
        UserBan userBan = new UserBan();
        userBan.setIdUser(user.getIdUser());
        userBan.setAttempt(0);
        userBan.setBlock(false);
        userBan.setDate(new Timestamp(System.currentTimeMillis()));
        return userBan;
    }

    private UserBan getUserBan(User user) {
        UserBan userBan = new UserBan();
        userBan.setIdUser(user.getIdUser());
        return getUserBan(userBan);
    }

    private User selectUser(User user) {
        return (User) transactionManager.execute(connection -> userDAO.select(connection, user));
    }

    private boolean isUserExist(User user) {
        return !isNull(user);
    }

    private UserBan getUserBan(UserBan user) {
        return (UserBan) transactionManager.execute(connection -> userBanDAO.select(connection, user));
    }

    private User getUserByEmail(User user) {
        return (User) transactionManager.execute(connection -> userDAO.selectUserByEmail(connection, user));
    }

    private void insertUserBan(UserBan user) {
        transactionManager.execute(connection -> userBanDAO.insert(connection, user));
    }

    private void updateUserBan(UserBan user) {
        transactionManager.execute(connection -> userBanDAO.update(connection, user));
    }
}
