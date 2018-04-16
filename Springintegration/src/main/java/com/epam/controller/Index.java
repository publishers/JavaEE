package com.epam.controller;

import com.epam.bean.validation.FilterValidator;
import com.epam.controller.pages.Pages;
import com.epam.database.entity.Goods;
import com.epam.database.search.SearchCriteria;
import com.epam.service.GoodsService;
import com.epam.service.ManufacturerService;
import com.epam.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class Index {
    @Autowired
    private TypeService typeService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FilterValidator filterValidator;


    @GetMapping("/index")
    protected String homeController(@RequestParam(required = false) Map<String, String> filter, HttpServletRequest request) {
        filterValidator.validate(filter);
        String queryString = clearQueryString(request.getQueryString());
        request.setAttribute("queryString", queryString);
        request.setAttribute("typeSelectAll", typeService.selectAll());
        request.setAttribute("manufacturerSelectAll", manufacturerService.selectAll());

        List<SearchCriteria> searchCriteria = buildSearchCriteria(filter);
        List<Goods> goodsList = goodsService.selectAllForPage(searchCriteria);
        long fullNumberGoods = goodsService.countGoods(searchCriteria);

        request.setAttribute("goods", goodsList);
        request.setAttribute("fullNumberGoods", fullNumberGoods);

        return Pages.INDEX;
    }

    private String clearQueryString(String queryString) {
        queryString = queryString == null ? "" : queryString;
        return queryString.replaceFirst("currentPage=\\d+&", "");
    }

    private List<SearchCriteria> buildSearchCriteria(Map<String, String> filter) {
        return filter.entrySet().stream()
                .map(stringStringEntry -> new SearchCriteria(stringStringEntry.getKey(), stringStringEntry.getValue()))
                .collect(Collectors.toList());
    }

}

