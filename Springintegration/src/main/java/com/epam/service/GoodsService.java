package com.epam.service;

import com.epam.bean.BeanFilters;
import com.epam.database.dao.GoodsDAO;
import com.epam.database.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    public Goods selectById(Goods goods) {
        return new Goods();
    }

    public List<Goods> selectAll(BeanFilters beanFilters) {
        return goodsDAO.findAll();
    }

    public int fullNumberGoods(BeanFilters beanFilters) {
        return (int) goodsDAO.count();
    }
}
