package com.epam.controller;

import com.epam.bean.BeanFilters;
import com.epam.controller.pages.Pages;
import com.epam.database.entity.Goods;
import com.epam.service.GoodsService;
import com.epam.service.ManufacturerService;
import com.epam.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class Index {
    @Autowired
    private TypeService typeService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/index")
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String queryString = clearQueryString(request.getQueryString());

        BeanFilters beanFilters = getBeanFilters(request);

        request.setAttribute("typeSelectAll", typeService.selectAll());
        request.setAttribute("manufacturerSelectAll", manufacturerService.selectAll());

        List<Goods> goodsList = goodsService.selectAll(beanFilters);
        int fullNumberGoods = goodsService.fullNumberGoods(beanFilters);
        request.setAttribute("queryString", queryString);
        request.setAttribute("goods", goodsList);
        request.setAttribute("fullNumberGoods", fullNumberGoods);

//        request.getRequestDispatcher().forward(request, response);
        return Pages.INDEX;
    }

    private String clearQueryString(String queryString) {
        queryString = queryString == null ? "" : queryString;
        return queryString.replaceFirst("currentPage=\\d+&", "");
    }

    private BeanFilters getBeanFilters(HttpServletRequest request) {
        return new BeanFilters(request);
    }
}

