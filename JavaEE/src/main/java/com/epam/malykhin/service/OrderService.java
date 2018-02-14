package com.epam.malykhin.service;

import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.OrderCartDAO;
import com.epam.malykhin.database.dao.OrderDAO;
import com.epam.malykhin.database.entity.Order;
import com.epam.malykhin.database.entity.User;

import javax.servlet.ServletContext;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/19/2016.
 */
public class OrderService implements Service {
    private OrderCartDAO orderCartDAO;
    private OrderDAO orderDAO;
    private int lastIdOrderCart;
    private TransactionManager transactionManager;

    @Override
    public void init(ServletContext context) {
        orderDAO = (OrderDAO) context.getAttribute(ORDER_DAO);
        orderCartDAO = (OrderCartDAO) context.getAttribute(ORDER_CART_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public boolean insert(Order order) {
        return (boolean) transactionManager.execute(connection -> {
            lastIdOrderCart = orderDAO.insert(connection, order);
            return orderCartDAO.insert(connection, order.getCart(), lastIdOrderCart);
        });
    }

    public boolean updateOrderStatus(Order order, int idOrderCart) {
        return (boolean) transactionManager.execute(connection -> orderDAO.update(connection, order, idOrderCart));
    }

    public int getLastIdOrderUser(User user) {
        return (int) transactionManager.execute(connection -> orderDAO.selectLastUserOrder(connection, user));

    }
}
