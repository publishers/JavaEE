package com.epam.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@ToString
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
}
