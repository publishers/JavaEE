package com.epam.malykhin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;


@ToString
public class BeanOrder {
    @Getter @Setter
    private String card;
    @Getter @Setter
    private String address;
    @Getter @Setter
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
