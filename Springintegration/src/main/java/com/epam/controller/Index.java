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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    protected String homeController(@RequestParam(required = false) Map<String, String> filter, Model model) {
        filterValidator.validate(filter);
        String queryString = clearQueryString(filter);
        model.addAttribute("queryString", queryString);
        model.addAttribute("typeSelectAll", typeService.selectAll());
        model.addAttribute("manufacturerSelectAll", manufacturerService.selectAll());

        List<SearchCriteria> searchCriteria = buildSearchCriteria(filter);
        List<Goods> goodsList = goodsService.selectAllForPage(searchCriteria);
        long fullNumberGoods = goodsService.countGoods(searchCriteria);

        model.addAttribute("goods", goodsList);
        model.addAttribute("fullNumberGoods", fullNumberGoods);

        return Pages.INDEX;
    }

    private String clearQueryString(Map<String, String> params) {
        String query = params.entrySet().stream()
                .map(stringStringEntry -> stringStringEntry.getKey() + "=" + stringStringEntry.getValue() + "&")
                .collect(Collectors.joining());
        return query.replaceAll("currentPage=\\d+&", "");

    }

    private List<SearchCriteria> buildSearchCriteria(Map<String, String> filter) {
        return filter.entrySet().stream()
                .map(stringStringEntry -> new SearchCriteria(stringStringEntry.getKey(), stringStringEntry.getValue()))
                .collect(Collectors.toList());
    }

}

