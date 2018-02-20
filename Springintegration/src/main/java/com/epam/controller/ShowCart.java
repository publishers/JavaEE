package com.epam.controller;

import com.epam.controller.pages.Pages;
import com.epam.database.entity.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.util.StaticTransformVariable.CART_SESSION;
import static java.util.Objects.isNull;

@Controller
public class ShowCart {

    @GetMapping("/showcart")
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        request.setAttribute("goods", cart.getCart());
//        request.getRequestDispatcher().forward(request, response);
        return Pages.CART;
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

}
