package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.ManufacturerDAO;
import com.epam.malykhin.database.entity.Manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Malykhin on 15.12.16.
 */
public class MySqlManufacturer implements ManufacturerDAO {

    private static final String TABLE_NAME = "manufacturer";

    @Override
    public Manufacturer select(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "` where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Manufacturer resultManufacturer = null;
        if (resultSet.next()) {
            resultManufacturer = extract(resultSet);
        }
        return resultManufacturer;
    }

    private Manufacturer extract(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("id"));
        manufacturer.setName(resultSet.getString("name"));
        return manufacturer;
    }

    @Override
    public List<Manufacturer> selectAll(Connection connection) throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "`");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            manufacturers.add(extract(resultSet));
        }
        return manufacturers;
    }
}
