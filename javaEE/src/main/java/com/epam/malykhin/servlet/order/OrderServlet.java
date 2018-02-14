package com.epam.malykhin.servlet.order;

import com.epam.malykhin.database.entity.Cart;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.malykhin.util.StaticTransformVariable.CART_SESSION;

/**
 * Created by Serhii_Malykhin on 12/20/2016.
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        request.setAttribute("goods", cart.getCart());
        request.getRequestDispatcher(Pages.ORDER).forward(request, response);
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

    private boolean isNull(Object object) {
        return object == null;
    }
}
