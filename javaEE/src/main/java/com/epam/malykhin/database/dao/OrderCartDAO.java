package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.Goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;


public interface OrderCartDAO {
    boolean insert(Connection connection, Map<Goods, Integer> cart, int idOrderCart) throws SQLException;
}
