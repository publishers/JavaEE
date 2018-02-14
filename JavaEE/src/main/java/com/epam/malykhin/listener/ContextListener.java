package com.epam.malykhin.listener;

import com.epam.malykhin.captcha.FactoryCaptcha;
import com.epam.malykhin.captcha.MapCaptchas;
import com.epam.malykhin.database.JdbcConnectionHolder;
import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.*;
import com.epam.malykhin.database.dao.mysql.*;
import com.epam.malykhin.filters.locale.CookieEpamStorageLocale;
import com.epam.malykhin.filters.locale.factory.FactoryEpamStorageLocale;
import com.epam.malykhin.service.*;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.epam.malykhin.util.StaticTransformVariable.*;

/**
 * Created by Serhii_Malykhin on 12/2/2016.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private FactoryEpamStorageLocale factoryEpamLocaleStorage = new FactoryEpamStorageLocale();
    private MapCaptchas mapCaptcha = new MapCaptchas();
    private FactoryCaptcha factoryCaptcha = new FactoryCaptcha();
    private JdbcConnectionHolder connectionHolder = new JdbcConnectionHolder();
    private UserDAO userDAO = new MySqlUser();
    private UserBanDAO userBanDAO = new MySqlUserBan();
    private TypeDAO typeDAO = new MySqlType();
    private GoodsDAO goodsDAO = new MySqlGoods();
    private OrderDAO orderDAO = new MySqlOrder();
    private OrderCartDAO orderCartDAO = new MySqlOrderCart();
    private ManufacturerDAO manufacturerDAO = new MySqlManufacturer();
    private UserService userService = new UserService();
    private TypeService typeService = new TypeService();
    private OrderService orderService = new OrderService();
    private GoodsService goodsService = new GoodsService();
    private UserServiceBan userServiceBan = new UserServiceBan();
    private ManufacturerService manufacturerService = new ManufacturerService();
    private TransactionManager transactionManager = new TransactionManager(connectionHolder);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        mapCaptcha.start();
        ServletContext servletContext = servletContextEvent.getServletContext();
        PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
        String captchaSaver = servletContext.getInitParameter("captcha");
        String typeLocaleStorage = servletContext.getInitParameter("typeLocaleStorage");
        factoryCaptcha.setCaptcha(captchaSaver);
        factoryEpamLocaleStorage.setEpamStorageLocale(typeLocaleStorage);
        initDAO(servletContext);
        String cookieMaxAge = servletContext.getInitParameter(COOKIE_MAX_AGE);
        if (typeLocaleStorage.equals("cookie")) {
            ((CookieEpamStorageLocale) factoryEpamLocaleStorage.getEpamStorageLocale()).setMaxAge(Integer.parseInt(cookieMaxAge));
        }
        initServices(servletContext);
        servletContext.setAttribute(COOKIE_MAX_AGE, cookieMaxAge);
        servletContext.setAttribute(TYPE_LOCALE_STORAGE, factoryEpamLocaleStorage.getEpamStorageLocale());
        servletContext.setAttribute(MAP_CAPTCHA, mapCaptcha);
        servletContext.setAttribute(SAVER_CAPTCHA, factoryCaptcha.getCaptcha());
        servletContext.setAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER, transactionManager);
    }

    private void initDAO(ServletContext servletContext) {
        servletContext.setAttribute(USER_DAO, userDAO);
        servletContext.setAttribute(TYPE_DAO, typeDAO);
        servletContext.setAttribute(ORDER_DAO, orderDAO);
        servletContext.setAttribute(GOODS_DAO, goodsDAO);
        servletContext.setAttribute(USER_BAN_DAO, userBanDAO);
        servletContext.setAttribute(ORDER_CART_DAO, orderCartDAO);
        servletContext.setAttribute(MANUFACTURER_DAO, manufacturerDAO);
    }

    private void initServices(ServletContext servletContext) {
        servletContext.setAttribute(ORDER_SERVICE, orderService);
        servletContext.setAttribute(USER_SERVICE, userService);
        servletContext.setAttribute(TYPE_SERVICE, typeService);
        servletContext.setAttribute(GOODS_SERVICE, goodsService);
        servletContext.setAttribute(USER_SERVICE_BAN, userServiceBan);
        servletContext.setAttribute(MANUFACTURER_SERVICE, manufacturerService);
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(USER_SERVICE);
        servletContext.removeAttribute(SAVER_CAPTCHA);
        servletContext.removeAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
        servletContext.removeAttribute(MAP_CAPTCHA);
        mapCaptcha.interrupt();
    }
}
