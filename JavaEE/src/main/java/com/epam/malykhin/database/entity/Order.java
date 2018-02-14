package com.epam.malykhin.database.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Serhii_Malykhin on 12/19/2016.
 */
public class Order implements Serializable {
    private int idUser;
    private long date;
    private StatusOrder statusOrder;
    private String descriptionStatus;
    private String address;
    private String paymentCardNumber;
    private Map<Goods, Integer> cart;
    private int totalPrice;

    public Order(int idUser, long date, StatusOrder statusOrder, String descriptionStatus,
                 String address, String paymentCardNumber, Map<Goods, Integer> cart) {
        this.idUser = idUser;
        this.date = date;
        this.statusOrder = statusOrder;
        this.descriptionStatus = descriptionStatus;
        this.address = address;
        this.paymentCardNumber = paymentCardNumber;
        this.cart = Collections.unmodifiableMap(new HashMap<>(cart));
        countTotalPrice();
    }

    private void countTotalPrice() {
        Iterator<Map.Entry<Goods, Integer>> cart = this.getCart().entrySet().iterator();
        while (cart.hasNext()) {
            Map.Entry<Goods, Integer> goods = cart.next();
            totalPrice += goods.getValue() * goods.getKey().getPrice();
        }
    }

    public int getIdUser() {
        return idUser;
    }

    public long getDate() {
        return date;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public Map<Goods, Integer> getCart() {
        return cart;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
