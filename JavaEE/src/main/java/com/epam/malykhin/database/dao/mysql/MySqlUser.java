package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.UserDAO;
import com.epam.malykhin.database.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
public class MySqlUser implements UserDAO {
    private static final String TABLE_NAME = "user";

    @Override
    public boolean update(Connection conn, User user) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User selectUserByEmail(Connection conn, User user) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("select * from `" + TABLE_NAME + "` where email=?");
        preparedStatement.setString(1, user.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        User resultUser = null;
        if (resultSet.next()) {
            resultUser = extract(resultSet);
        }
        return resultUser;
    }

    @Override
    public User select(Connection conn, User user) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("select * from `" + TABLE_NAME + "` where email=? and password=?");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        User resultUser = null;
        if (resultSet.next()) {
            resultUser = extract(resultSet);
        }
        return resultUser;
    }

    private User extract(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setIdUser(resultSet.getInt("iduser"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setSecondName(resultSet.getString("secondName"));
        user.setNewsletter(resultSet.getBoolean("newsletter"));
        user.setRoleId(resultSet.getInt("role_id"));
        return user;
    }

    @Override
    public boolean delete(Connection conn, final User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(Connection conn, final User user) throws SQLException {
        PreparedStatement preparedStatement =
                conn.prepareStatement("insert into `" + TABLE_NAME + "` (`firstname`, `secondname`, `email`, `password`, `newsletter`) values (?,?,?,?,?)");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getSecondName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setBoolean(5, user.isNewsletter());
        return preparedStatement.executeUpdate() == 1;
    }
}
