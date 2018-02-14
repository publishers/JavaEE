package com.epam.malykhin.service;

import com.epam.malykhin.bean.BeanFilters;
import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.GoodsDAO;
import com.epam.malykhin.database.entity.Goods;

import javax.servlet.ServletContext;
import java.util.List;

import static com.epam.malykhin.util.StaticTransformVariable.CONTEXT_LISTENER_TRANSACTION_MANAGER;
import static com.epam.malykhin.util.StaticTransformVariable.GOODS_DAO;

/**
 * Created by Serhii_Malykhin on 12/15/2016.
 */
public class GoodsService implements Service {

    private GoodsDAO goodsDAO;
    private TransactionManager transactionManager;

    @Override
    public void init(ServletContext context) {
        goodsDAO = (GoodsDAO) context.getAttribute(GOODS_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public Goods selectById(Goods goods) {
        return (Goods) transactionManager.execute(connection -> goodsDAO.select(connection, goods));
    }

    public List<Goods> selectAll(BeanFilters beanFilters) {
        return (List<Goods>) transactionManager.execute(connection -> goodsDAO.select(connection, beanFilters));
    }

    public int fullNumberGoods(BeanFilters beanFilters) {
        return (int) transactionManager.execute(connection -> goodsDAO.getNumberGoods(connection, beanFilters));
    }

    public boolean isServiceOk() {
        return goodsDAO != null && transactionManager != null;
    }
}
