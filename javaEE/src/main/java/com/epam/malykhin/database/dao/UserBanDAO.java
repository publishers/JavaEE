package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.UserBan;

import java.sql.Connection;
import java.sql.SQLException;


public interface UserBanDAO {
    UserBan select(Connection connection, UserBan user) throws SQLException;

    boolean insert(Connection connection, UserBan user) throws SQLException;

    boolean update(Connection connection, UserBan user) throws SQLException;
}
