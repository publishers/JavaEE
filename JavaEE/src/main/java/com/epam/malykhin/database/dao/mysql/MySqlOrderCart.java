package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.database.dao.OrderCartDAO;
import com.epam.malykhin.database.entity.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Serhii_Malykhin on 12/21/2016.
 */
public class MySqlOrderCart implements OrderCartDAO {
    private static final String TABLE_NAME = "ordercart";

    @Override
    public boolean insert(Connection connection, Map<Goods, Integer> cart, int idOrderCart) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into `" + TABLE_NAME + "` values (?,?,?,?,?,?,?,?)");
        Iterator<Map.Entry<Goods, Integer>> iterator = cart.entrySet().iterator();
        while (iterator.hasNext()) {
            pushGoods(preparedStatement, idOrderCart, iterator.next());
            preparedStatement.executeUpdate();
        }
        return true;
    }

    private void pushGoods(PreparedStatement preparedStatement, int idOrderCart, Map.Entry<Goods, Integer> nextGoods) throws SQLException {
        Goods goods = nextGoods.getKey();
        preparedStatement.setInt(1, idOrderCart);
        preparedStatement.setString(2, goods.getTitle());
        preparedStatement.setString(3, goods.getDescription());
        preparedStatement.setInt(4, goods.getPrice());
        preparedStatement.setString(5, goods.getSrcImg());
        preparedStatement.setInt(6, goods.getIdType());
        preparedStatement.setInt(7, goods.getIdManufacturer());
        preparedStatement.setInt(8, nextGoods.getValue());
    }
}
