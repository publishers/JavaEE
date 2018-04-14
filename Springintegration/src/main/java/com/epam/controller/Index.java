package com.epam.controller;

import com.epam.bean.BeanFilter;
import com.epam.controller.pages.Pages;
import com.epam.database.entity.Goods;
import com.epam.service.GoodsService;
import com.epam.service.ManufacturerService;
import com.epam.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Index {
    @Autowired
    private TypeService typeService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    @Qualifier("beanFilterValidator")
    private Validator filterValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(filterValidator);
    }

    @GetMapping("/index")
    protected String homeController(@Validated BeanFilter filter, HttpServletRequest request) {
        String queryString = clearQueryString(request.getQueryString());
        request.setAttribute("queryString", queryString);

        request.setAttribute("typeSelectAll", typeService.selectAll());
        request.setAttribute("manufacturerSelectAll", manufacturerService.selectAll());

        List<Goods> goodsList = goodsService.selectAllForPage(filter);
        long fullNumberGoods = goodsService.countGoods(filter);
        request.setAttribute("goods", goodsList);
        request.setAttribute("fullNumberGoods", fullNumberGoods);

        return Pages.INDEX;
    }

    private String clearQueryString(String queryString) {
        queryString = queryString == null ? "" : queryString;
        return queryString.replaceFirst("currentPage=\\d+&", "");
    }

}

