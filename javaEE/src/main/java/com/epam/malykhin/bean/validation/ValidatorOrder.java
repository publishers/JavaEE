package com.epam.malykhin.bean.validation;

import com.epam.malykhin.bean.BeanOrder;

/**
 * Created by Serhii Malykhin on 21.12.16.
 */
public class ValidatorOrder {
    private static final String CARD = "^((\\d{16})|(\\d{4} \\d{4} \\d{4} \\d{4}))$";
    private static final String ADDRESS = "^.{5,}$";
    private boolean isValidOrder = true;
    private BeanOrder beanOrder;

    public ValidatorOrder(BeanOrder beanOrder) {
        this.beanOrder = beanOrder;
    }

    public void start() {
        String typeOfPayment = beanOrder.getTypeOfPayment();
        if (typeOfPayment == null) {
            isValidOrder = false;
            return;
        }

        if (typeOfPayment.equals("1")) {
            String card = beanOrder.getCard();
            isValidOrder = card.matches(CARD);
        }

        if (isValidOrder) {
            beanOrder.setCard("cash");
            isValidOrder = beanOrder.getAddress().matches(ADDRESS);
        }
    }

    public boolean isValidOrder() {
        return isValidOrder;
    }

    public BeanOrder getBeanOrder() {
        return beanOrder;
    }
}
