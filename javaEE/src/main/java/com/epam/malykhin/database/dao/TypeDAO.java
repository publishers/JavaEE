package com.epam.malykhin.database.dao;

import com.epam.malykhin.database.entity.Type;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TypeDAO {
    Type select(Connection connection, int id) throws SQLException;

    List<Type> selectAll(Connection connection) throws SQLException;
}
