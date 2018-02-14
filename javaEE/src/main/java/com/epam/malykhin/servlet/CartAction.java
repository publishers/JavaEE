package com.epam.malykhin.servlet;


import com.epam.malykhin.database.entity.Action;
import com.epam.malykhin.database.entity.Cart;
import com.epam.malykhin.database.entity.Goods;
import com.epam.malykhin.service.GoodsService;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.CART_SESSION;
import static com.epam.malykhin.util.StaticTransformVariable.GOODS_SERVICE;
import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 17.12.16.
 */
@WebServlet("/cart")
public class CartAction extends HttpServlet {
    private GoodsService goodsService;
    private Map<String, Action> actionMap;

    @Override
    public void init() throws ServletException {
        goodsService = (GoodsService) getServletContext().getAttribute(GOODS_SERVICE);
        goodsService.init(getServletContext());
        initActionMap();
    }

    private void initActionMap() {
        actionMap = new HashMap<>();
        actionMap.put("add", (cart, goods) -> cart.add(goods));
        actionMap.put("remove", (cart, goods) -> cart.delete(goods));
        actionMap.put("clear", (cart, goods) -> cart.clear(goods));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idGoods = checkIdGoods(request.getParameter("idGoods"));
        if (isNull(idGoods)) {
            response.sendError(400, "Wrong query!\n Please, check your query!");
            return;
        }

        Goods goods = new Goods();
        goods.setId(Integer.parseInt(idGoods));

        goods = goodsService.selectById(goods);
        JSONObject json = new JSONObject();
        Cart cart = getCart(request);
        doAction(goods, cart, action, json);
        sendJSONResponse(response, json);
    }

    private void doAction(Goods goods, Cart cart, String action, JSONObject json) {
        if (!action.equals("updateGoodsData")) {
            actionMap.get(action).doAction(cart, goods);
            json.put("orderSum", cart.getSumOfOrder());
            json.put("orderCountGoods", cart.countGoods());
        } else {
            json.put("count", cart.getCart().get(goods));
            json.put("priceGoods", goods.getPrice());
        }
    }

    private Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_SESSION);
        if (isNull(cart)) {
            cart = new Cart();
            session.setAttribute(CART_SESSION, cart);
        }
        return cart;
    }

    private String checkIdGoods(String idGoods) {
        return idGoods.matches("\\d+") ? idGoods : null;
    }

    public void sendJSONResponse(HttpServletResponse response, JSONObject json) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.print(json.toString());
    }
}
