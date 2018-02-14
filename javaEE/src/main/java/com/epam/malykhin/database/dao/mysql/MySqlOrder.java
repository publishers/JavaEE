package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.OrderDAO;
import com.epam.malykhin.database.entity.Order;
import com.epam.malykhin.database.entity.User;
import com.mysql.cj.api.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Serhii Malykhin on 20.12.16.
 */
public class MySqlOrder implements OrderDAO {
    private static final String TABLE_NAME = "order";

    @Override
    public int insert(Connection connection, Order order) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("insert into `" + TABLE_NAME + "` " +
                                "(`idUser`, `idOrderCart`, `date`, `idStatus`, " +
                                "`descriptionStatus`, `address`, `card`) " +
                                "values (?,DEFAULT,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.getIdUser());
        preparedStatement.setLong(2, order.getDate());
        preparedStatement.setInt(3, order.getStatusOrder().getMask());
        preparedStatement.setString(4, order.getDescriptionStatus());
        preparedStatement.setString(5, order.getAddress());
        preparedStatement.setString(6, order.getPaymentCardNumber());
        preparedStatement.execute();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        return -1;
    }

    @Override
    public boolean update(Connection connection, Order order, int idOrderCart) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update `" + TABLE_NAME + "`  set idStatus=?, descriptionStatus=? where idOrderCart=?");

        preparedStatement.setInt(1, order.getStatusOrder().getMask());
        preparedStatement.setString(2, order.getDescriptionStatus());
        preparedStatement.setInt(3, idOrderCart);
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public Order getOrderList(Connection connection, User user) {
        return null;
    }

    @Override
    public Object selectLastUserOrder(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "` where iduser=? order by idOrderCart desc");

        preparedStatement.setInt(1, user.getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer lastOrderIdUserGoods = null;
        if (resultSet.next()) {
            lastOrderIdUserGoods = resultSet.getInt("idOrderCart");
        }
        return lastOrderIdUserGoods;
    }
}
