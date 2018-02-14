package com.epam.malykhin.bean;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Serhii Malykhin on 21.12.16.
 */
public class BeanOrder {
    private String card;
    private String address;
    private String typeOfPayment;

    public BeanOrder(HttpServletRequest request) {
        init(request);
    }

    private void init(HttpServletRequest request) {
        card = request.getParameter("card");
        address = request.getParameter("address");
        typeOfPayment = request.getParameter("typeOfPayment");
    }

    public String getCard() {
        return card;
    }

    public String getAddress() {
        return address;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }
}
