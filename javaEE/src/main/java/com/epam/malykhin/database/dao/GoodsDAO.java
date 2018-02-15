package com.epam.malykhin.database.dao;

import com.epam.malykhin.bean.BeanFilters;
import com.epam.malykhin.database.entity.Goods;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface GoodsDAO {

    Goods select(Connection connection, Goods goods) throws SQLException;

    Goods insert(Connection connection, Goods goods);

    int getNumberGoods(Connection connection, BeanFilters beanFilters) throws SQLException;

    List<Goods> select(Connection connection, BeanFilters beanFilters) throws SQLException;
}
