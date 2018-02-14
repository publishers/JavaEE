package com.epam.malykhin.database.dao.mysql;

import com.epam.malykhin.bean.BeanFilters;
import com.epam.malykhin.database.dao.GoodsDAO;
import com.epam.malykhin.database.dao.filter.MySQLQueryBuilderFilter;
import com.epam.malykhin.database.entity.Goods;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii_Malykhin on 12/13/2016.
 */
public class MySqlGoods implements GoodsDAO {
    private static final Logger LOG = Logger.getLogger(MySqlGoods.class);
    private static final String TABLE_NAME = "goods";
    private MySQLQueryBuilderFilter mySQLQueryBuilderFilter;

    @Override
    public Goods select(Connection connection, Goods goods) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "` where id=?");
        preparedStatement.setInt(1, goods.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return extract(resultSet);
        }
        return null;
    }

    @Override
    public Goods insert(Connection connection, Goods goods) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getNumberGoods(Connection connection, BeanFilters beanFilters) throws SQLException {
        mySQLQueryBuilderFilter = new MySQLQueryBuilderFilter(beanFilters);
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from `" + TABLE_NAME + "` " + mySQLQueryBuilderFilter.getQueryFilterWhere());
        List listParamWhere = new ArrayList(mySQLQueryBuilderFilter.getFilterWhere().getBeansWhereFilters().values());
        setupPreparedStatement(preparedStatement, listParamWhere);
        LOG.debug(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count");
        }
        return -1;
    }

    @Override
    public List<Goods> select(Connection connection, BeanFilters beanFilters) throws SQLException {
        mySQLQueryBuilderFilter = new MySQLQueryBuilderFilter(beanFilters);
        List<Goods> goodsList = new ArrayList<>();
        LOG.debug("select * from `" + TABLE_NAME + "` " + mySQLQueryBuilderFilter.getQuery());
        PreparedStatement preparedStatement = connection.prepareStatement("select * from `" + TABLE_NAME + "` " + mySQLQueryBuilderFilter.getQuery());
        setupPreparedStatement(preparedStatement, mySQLQueryBuilderFilter.getList());
        LOG.debug(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            goodsList.add(extract(resultSet));
        }
        return goodsList;
    }

    private Goods extract(ResultSet result) throws SQLException {
        Goods goods = new Goods();

        goods.setId(result.getInt("id"));
        goods.setTitle(result.getString("title"));
        goods.setDescription(result.getString("description"));
        goods.setPrice(result.getInt("price"));
        goods.setSrcImg(result.getString("srcImg"));
        goods.setIdType(result.getInt("idType"));
        goods.setIdManufacturer(result.getInt("idManufacture"));
        return goods;
    }

    public void setupPreparedStatement(PreparedStatement preparedStatement, List parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            preparedStatement.setObject(i + 1, parameters.get(i));
        }
    }
}
