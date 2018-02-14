package com.epam.malykhin.servlet;

import com.epam.malykhin.bean.BeanFilters;
import com.epam.malykhin.database.entity.Goods;
import com.epam.malykhin.service.GoodsService;
import com.epam.malykhin.service.ManufacturerService;
import com.epam.malykhin.service.TypeService;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
@WebServlet("/index")
public class Index extends HttpServlet {
    private TypeService typeService;
    private ManufacturerService manufacturerService;
    private GoodsService goodsService;

    @Override
    public void init() throws ServletException {
        typeService = (TypeService) getServletContext().getAttribute(TYPE_SERVICE);
        manufacturerService = (ManufacturerService) getServletContext().getAttribute(MANUFACTURER_SERVICE);
        goodsService = (GoodsService) getServletContext().getAttribute(GOODS_SERVICE);
        initService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String queryString = clearQueryString(request.getQueryString());

        BeanFilters beanFilters = getBeanFilters(request);

        request.setAttribute("typeSelectAll", typeService.selectAll());
        request.setAttribute("manufacturerSelectAll", manufacturerService.selectAll());

        List<Goods> goodsList = goodsService.selectAll(beanFilters);
        int fullNumberGoods = goodsService.fullNumberGoods(beanFilters);
        request.setAttribute("queryString", queryString);
        request.setAttribute("goods", goodsList);
        request.setAttribute("fullNumberGoods", fullNumberGoods);

        request.getRequestDispatcher(Pages.INDEX).forward(request, response);
    }

    private String clearQueryString(String queryString) {
        queryString = queryString == null ? "" : queryString;
        return queryString.replaceFirst("currentPage=\\d+&", "");
    }

    private void initService(ServletContext servletContext) {
        typeService.init(servletContext);
        manufacturerService.init(servletContext);
        goodsService.init(servletContext);
    }

    private BeanFilters getBeanFilters(HttpServletRequest request) {
        return new BeanFilters(request);
    }
}
