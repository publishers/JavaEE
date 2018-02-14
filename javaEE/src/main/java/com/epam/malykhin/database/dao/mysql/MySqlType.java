package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.TypeDAO;
import com.epam.malykhin.database.entity.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Malykhin on 15.12.16.
 */
public class MySqlType implements TypeDAO {
    private static final String TABLE_NAME = "type";

    @Override
    public Type select(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "` where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Type resultType = null;
        if (resultSet.next()) {
            resultType = extract(resultSet);
        }
        return resultType;
    }

    private Type extract(ResultSet resultSet) throws SQLException {
        Type type = new Type();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    }

    @Override
    public List<Type> selectAll(Connection connection) throws SQLException {
        List<Type> types = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "`");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            types.add(extract(resultSet));
        }
        return types;
    }
}
