package com.epam.service;

import com.epam.database.dao.OrderDAO;
import com.epam.database.entity.Order;
import com.epam.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    //    @Autowired
//    private OrderCartDAO orderCartDAO;
    @Autowired
    private OrderDAO orderDAO;
    private int lastIdOrderCart;


    public boolean insert(Order order) {
//        return (boolean) transactionManager.execute(connection -> {
//            lastIdOrderCart = orderDAO.insert(connection, order);
//            return orderCartDAO.insert(connection, order.getCart(), lastIdOrderCart);
//        });
        return false;
    }

    public boolean updateOrderStatus(Order order, int idOrderCart) {
//        return (boolean) transactionManager.execute(connection -> orderDAO.update(connection, order, idOrderCart));
        return false;
    }

    public int getLastIdOrderUser(User user) {
//        return (int) transactionManager.execute(connection -> orderDAO.selectLastUserOrder(connection, user));
        return 0;
    }
}
