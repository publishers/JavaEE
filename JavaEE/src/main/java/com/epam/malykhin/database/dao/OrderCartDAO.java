package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.Goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Serhii_Malykhin on 12/21/2016.
 */
public interface OrderCartDAO {
    boolean insert(Connection connection, Map<Goods, Integer> cart, int idOrderCart) throws SQLException;
}
