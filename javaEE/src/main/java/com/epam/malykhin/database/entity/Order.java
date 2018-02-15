package com.epam.malykhin.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@Setter
@ToString
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
        for (Map.Entry<Goods, Integer> goods : this.getCart().entrySet()) {
            totalPrice += goods.getValue() * goods.getKey().getPrice();
        }
    }
}
