package com.epam.malykhin.servlet.order;

import com.epam.malykhin.bean.BeanOrder;
import com.epam.malykhin.bean.validation.ValidatorOrder;
import com.epam.malykhin.database.entity.Cart;
import com.epam.malykhin.database.entity.Order;
import com.epam.malykhin.database.entity.StatusOrder;
import com.epam.malykhin.database.entity.User;
import com.epam.malykhin.service.OrderService;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii Malykhin on 21.12.16.
 */
@WebServlet("/confirm")
public class OrderConfirm extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute(ORDER_SERVICE);
        orderService.init(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = (Order) request.getSession().getAttribute(ORDER_SESSION);
        if (order == null) {
            response.sendRedirect(Pages.SERVLET_INDEX);
            return;
        }
        request.setAttribute("order", order);
        request.setAttribute("goods", order.getCart());
        request.setAttribute("orderId", orderService.getLastIdOrderUser(getUser(request)));
        request.getRequestDispatcher(Pages.ORDER_CONFIRM).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCart(request);
        BeanOrder beanOrder = getBeanOrder(request);
        ValidatorOrder validatorOrder = getValidatorOrder(beanOrder);
        validatorOrder.start();
        if (!validatorOrder.isValidOrder()) {
            response.sendRedirect(Pages.SERVLET_ORDER);
            return;
        }
        Order order = getOrder(request, cart, validatorOrder, StatusOrder.FORMED, "Order formed by user");
        if (!orderService.insert(order)) {
            response.sendError(500, "Something is getting wrong on server side. ");
            return;
        }
        order = getOrder(request, cart, validatorOrder, StatusOrder.SENT, "Order is saved into database");
        orderService.updateOrderStatus(order, orderService.getLastIdOrderUser(getUser(request)));
        request.getSession().setAttribute(ORDER_SESSION, order);
        response.sendRedirect(request.getRequestURI());
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


    private Order getOrder(HttpServletRequest request, Cart cart, ValidatorOrder validatorOrder, StatusOrder statusOrder, String describe) {
        User user = getUser(request);
        Date date = new Date();
        return new Order(user.getIdUser(), date.getTime(),
                statusOrder, describe, validatorOrder.getBeanOrder().getAddress(),
                validatorOrder.getBeanOrder().getCard(), cart.getCart());
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_SESSION);
        return user;
    }

    private boolean isNull(Object object) {
        return object == null;
    }

    private ValidatorOrder getValidatorOrder(BeanOrder beanOrder) {
        return new ValidatorOrder(beanOrder);
    }

    private BeanOrder getBeanOrder(HttpServletRequest request) {
        return new BeanOrder(request);
    }
}
