package com.epam.service;

import com.epam.database.dao.UserBanDAO;
import com.epam.database.dao.UserDAO;
import com.epam.database.entity.User;
import com.epam.database.entity.UserBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

import static java.util.Objects.isNull;

@Service
public class UserServiceBan {
    @Autowired
    private UserBanDAO userBanDAO;

    @Autowired
    private UserDAO userDAO;

    private int attempts = 4;


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
        return userBan.getDate().getTime() - System.currentTimeMillis() >= 0 || isCorrectUser(tempUser, userBan, user, true);
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
        throw new UnsupportedOperationException();
    }

    private boolean isUserExist(User user) {
        return !isNull(user);
    }

    private UserBan getUserBan(UserBan user) {
        throw new UnsupportedOperationException();
    }

    private User getUserByEmail(User user) {
        throw new UnsupportedOperationException();
    }

    private void insertUserBan(UserBan user) {

    }

    private void updateUserBan(UserBan user) {
    }
}
