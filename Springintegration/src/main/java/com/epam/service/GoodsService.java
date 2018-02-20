package com.epam.service;

import com.epam.bean.BeanFilters;
import com.epam.database.dao.GoodsDAO;
import com.epam.database.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

//    @Autowired
//    private TransactionManager transactionManager;


    public Goods selectById(Goods goods) {
//        return (Goods) transactionManager.execute(connection -> goodsDAO.select(connection, goods));
        return new Goods();
    }

    public List<Goods> selectAll(BeanFilters beanFilters) {
//        return (List<Goods>) transactionManager.execute(connection -> goodsDAO.select(connection, beanFilters));
        return Collections.emptyList();
    }

    public int fullNumberGoods(BeanFilters beanFilters) {
//        return (int) transactionManager.execute(connection -> goodsDAO.getNumberGoods(connection, beanFilters));
        return 0;
    }

//    public boolean isServiceOk() {
//        return goodsDAO != null;
//    }
}
