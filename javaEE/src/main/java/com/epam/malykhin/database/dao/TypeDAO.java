package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.Type;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Serhii Malykhin on 15.12.16.
 */
public interface TypeDAO {
    Type select(Connection connection, int id) throws SQLException;

    List<Type> selectAll(Connection connection) throws SQLException;
}
