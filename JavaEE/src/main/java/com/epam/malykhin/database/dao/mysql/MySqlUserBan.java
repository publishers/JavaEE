package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.UserBanDAO;
import com.epam.malykhin.database.entity.UserBan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/30/2016.
 */
public class MySqlUserBan implements UserBanDAO {
    private static final String TABLE_NAME = "user_ban";

    @Override
    public UserBan select(Connection connection, UserBan user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select * from `" + TABLE_NAME + "` where iduser=?");
        preparedStatement.setInt(1, user.getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        UserBan userBan = null;
        if (resultSet.next()) {
            userBan = extractUserBan(resultSet);
        }
        return userBan;
    }

    @Override
    public boolean insert(Connection connection, UserBan user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("insert into `" + TABLE_NAME + "` values (?,?,?,?)");
        preparedStatement.setInt(1, user.getIdUser());
        preparedStatement.setTimestamp(2, user.getDate());
        preparedStatement.setBoolean(3, user.isBlock());
        preparedStatement.setInt(4, user.getAttempt());
        return preparedStatement.executeUpdate() != 0;
    }

    @Override
    public boolean update(Connection connection, UserBan user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("update  `" + TABLE_NAME + "` set timestart=?, block=?, attempt=? where iduser=?");
        preparedStatement.setTimestamp(1, user.getDate());
        preparedStatement.setBoolean(2, user.isBlock());
        preparedStatement.setInt(3, user.getAttempt());
        preparedStatement.setInt(4, user.getIdUser());
        return preparedStatement.executeUpdate() != 0;
    }

    private UserBan extractUserBan(ResultSet resultSet) throws SQLException {
        UserBan userBan = new UserBan();
        userBan.setBlock(resultSet.getBoolean("block"));
        userBan.setDate(resultSet.getTimestamp("timestart"));
        userBan.setIdUser(resultSet.getInt("iduser"));
        userBan.setAttempt(resultSet.getInt("attempt"));
        return userBan;
    }
}