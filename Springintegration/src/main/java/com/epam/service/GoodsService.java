package com.epam.service;

import com.epam.bean.BeanFilters;
import com.epam.database.dao.GoodsDAO;
import com.epam.database.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    public Goods selectById(Goods goods) {
        return new Goods();
    }

    public List<Goods> selectAll(BeanFilters beanFilters) {
        Map<String, String> beans = beanFilters.getBeans();
        int size = 10;
        int page = 0;
        String numberGoods;
        String currentPage;
        if ((numberGoods = beans.get("numberGoods")) != null && !numberGoods.trim().equals("") && (Integer.parseInt(numberGoods) > 0)) {
            size = Integer.parseInt(numberGoods);
        }
        if ((currentPage = beans.get("currentPage")) != null && !currentPage.trim().equals("") && (Integer.parseInt(currentPage) > 0)) {
            page = Integer.parseInt(currentPage);
            page--;
        }
        return goodsDAO.findAll(new PageRequest(page, size)).getContent();
    }

    public int fullNumberGoods(BeanFilters beanFilters) {
        return (int) goodsDAO.count();
    }
}
