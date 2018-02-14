package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.UserBan;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/30/2016.
 */
public interface UserBanDAO {
    UserBan select(Connection connection, UserBan user) throws SQLException;

    boolean insert(Connection connection, UserBan user) throws SQLException;

    boolean update(Connection connection, UserBan user) throws SQLException;
}
