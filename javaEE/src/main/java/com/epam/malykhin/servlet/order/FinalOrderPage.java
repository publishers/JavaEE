package com.epam.malykhin.servlet.order;

import com.epam.malykhin.database.entity.Order;
import com.epam.malykhin.database.entity.StatusOrder;
import com.epam.malykhin.service.OrderService;
import com.epam.malykhin.servlet.pages.Pages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.malykhin.util.StaticTransformVariable.CART_SESSION;
import static com.epam.malykhin.util.StaticTransformVariable.ORDER_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.ORDER_SESSION;


@WebServlet("/finalOrderPage")
public class FinalOrderPage extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute(ORDER_SERVICE);
        orderService.init(getServletContext());
    }

    // TODO: need refactoring
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderId = request.getParameter("orderId");
        if (!isNull(action) && !isNull(orderId) && orderId.matches("\\d+")) {
            Order order = getOrder(request);
            if (!isNull(order)) {
                StatusOrder statusOrder;
                if ((statusOrder = getStatus(action, order.getStatusOrder())) != order.getStatusOrder()) {
                    String description = "User " + statusOrder.getDescriptionStatus()+ " order!";
                    order = getOrder(order, statusOrder, description);
                    orderService.updateOrderStatus(order, Integer.parseInt(orderId));
                    removeOrderAndCart(request);
                    request.setAttribute("goods", order.getCart());
                    request.setAttribute("totalPrice", order.getTotalPrice());
                }
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher(Pages.FINAL_ORDER_PAGE).forward(request, response);
                return;
            }
        }
        response.sendRedirect(Pages.SERVLET_INDEX);
    }

    private void removeOrderAndCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(ORDER_SESSION);
        session.removeAttribute(CART_SESSION);
    }

    private StatusOrder getStatus(String action, StatusOrder oldStatus) {
        switch (action) {
            case "cancel":
                return StatusOrder.CANCELLED;
            case "confirm":
                return StatusOrder.CONFIRMED;
        }
        return oldStatus;
    }

    private Order getOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Order) session.getAttribute(ORDER_SESSION);
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    private Order getOrder(Order order, StatusOrder statusOrder, String describe) {
        return new Order(order.getIdUser(), order.getDate(),
                statusOrder, describe, order.getAddress(),
                order.getPaymentCardNumber(), order.getCart());
    }
}
