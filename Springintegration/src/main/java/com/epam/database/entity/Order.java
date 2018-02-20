package com.epam.database.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@Table(name = "order")
@Entity
public class Order implements Serializable {
    @Id
    @Column(name = "iduser")
    private int idUser;
    @Column(name = "date")
    private long date;
    @Column(name = "idStatus")
    private StatusOrder statusOrder;
    @Column(name = "descriptionStatus")
    private String descriptionStatus;
    @Column(name = "address")
    private String address;
    @Column(name = "card")
    private String paymentCardNumber;
    @Transient
    private Map<Goods, Integer> cart;
    @Transient
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
}
