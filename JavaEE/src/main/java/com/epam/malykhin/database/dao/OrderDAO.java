package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.Order;
import com.epam.malykhin.database.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Serhii Malykhin on 20.12.16.
 */
public interface OrderDAO {
    int insert(Connection connection, Order order) throws SQLException;

    boolean update(Connection connection, Order order, int idOrderCart) throws SQLException;

    Order getOrderList(Connection connection, User user);

    Object selectLastUserOrder(Connection connection, User user) throws SQLException;
}
