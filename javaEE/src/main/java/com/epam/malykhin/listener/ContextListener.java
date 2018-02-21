package com.epam.malykhin.listener;

import com.epam.malykhin.captcha.FactoryCaptcha;
import com.epam.malykhin.captcha.MapCaptchas;
import com.epam.malykhin.database.JdbcConnectionHolder;
import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.GoodsDAO;
import com.epam.malykhin.database.dao.ManufacturerDAO;
import com.epam.malykhin.database.dao.OrderCartDAO;
import com.epam.malykhin.database.dao.OrderDAO;
import com.epam.malykhin.database.dao.TypeDAO;
import com.epam.malykhin.database.dao.UserBanDAO;
import com.epam.malykhin.database.dao.UserDAO;
import com.epam.malykhin.database.dao.mysql.MySqlGoods;
import com.epam.malykhin.database.dao.mysql.MySqlManufacturer;
import com.epam.malykhin.database.dao.mysql.MySqlOrder;
import com.epam.malykhin.database.dao.mysql.MySqlOrderCart;
import com.epam.malykhin.database.dao.mysql.MySqlType;
import com.epam.malykhin.database.dao.mysql.MySqlUser;
import com.epam.malykhin.database.dao.mysql.MySqlUserBan;
import com.epam.malykhin.filters.locale.CookieEpamStorageLocale;
import com.epam.malykhin.filters.locale.factory.FactoryEpamStorageLocale;
import com.epam.malykhin.service.GoodsService;
import com.epam.malykhin.service.ManufacturerService;
import com.epam.malykhin.service.OrderService;
import com.epam.malykhin.service.TypeService;
import com.epam.malykhin.service.UserService;
import com.epam.malykhin.service.UserServiceBan;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.epam.malykhin.util.StaticTransformVariable.CONTEXT_LISTENER_TRANSACTION_MANAGER;
import static com.epam.malykhin.util.StaticTransformVariable.COOKIE_MAX_AGE;
import static com.epam.malykhin.util.StaticTransformVariable.GOODS_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.GOODS_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.MANUFACTURER_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.MANUFACTURER_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.MAP_CAPTCHA;
import static com.epam.malykhin.util.StaticTransformVariable.ORDER_CART_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.ORDER_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.ORDER_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.SAVER_CAPTCHA;
import static com.epam.malykhin.util.StaticTransformVariable.TYPE_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.TYPE_LOCALE_STORAGE;
import static com.epam.malykhin.util.StaticTransformVariable.TYPE_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.USER_BAN_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.USER_DAO;
import static com.epam.malykhin.util.StaticTransformVariable.USER_SERVICE;
import static com.epam.malykhin.util.StaticTransformVariable.USER_SERVICE_BAN;


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
