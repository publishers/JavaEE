package com.epam.malykhin.servlet.pages;

import java.io.File;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
public class Pages {
    public static final String SERVLET_ACCOUNT = "authorization";
    public static final String SERVLET_INDEX = "index";
    public static final String SERVLET_REGISTRATION = "registration";
    public static final String SERVLET_LOGOUT = "logout";
    public static final String SERVLET_ORDER = "order";
    public static final String SERVLET_CONFIRM = "confirm";
    private static final String PATH = "WEB-INF" + File.separator + "pages" + File.separator;
    public static final String ACCOUNT = PATH + "account.jsp";
    public static final String INDEX = PATH + "index.jsp";
    public static final String REGISTRATION = PATH + "register.jsp";
    public static final String CART = PATH + "cart.jsp";
    public static final String ORDER = PATH + "order.jsp";
    public static final String ORDER_CONFIRM = PATH + "orderConfirm.jsp";
    public static final String FINAL_ORDER_PAGE = PATH + "finalOrderPage.jsp";
    public static final String ERROR_ACCESS_PAGE = PATH + "error" + File.separator + "error403.jsp";
    public static final String ADMIN = PATH + "onlyAdmin.jsp";
}
