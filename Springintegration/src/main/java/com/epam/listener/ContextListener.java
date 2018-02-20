package com.epam.listener;

import com.epam.captcha.FactoryCaptcha;
import com.epam.captcha.MapCaptchas;
import com.epam.filters.locale.CookieEpamStorageLocale;
import com.epam.filters.locale.factory.FactoryEpamStorageLocale;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import static com.epam.util.StaticTransformVariable.COOKIE_MAX_AGE;
import static com.epam.util.StaticTransformVariable.MAP_CAPTCHA;
import static com.epam.util.StaticTransformVariable.SAVER_CAPTCHA;
import static com.epam.util.StaticTransformVariable.TYPE_LOCALE_STORAGE;


@WebListener
public class ContextListener extends ContextLoaderListener {
    private FactoryEpamStorageLocale factoryEpamLocaleStorage = new FactoryEpamStorageLocale();
    private MapCaptchas mapCaptcha = new MapCaptchas();
    private FactoryCaptcha factoryCaptcha = new FactoryCaptcha();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        mapCaptcha.start();
        ServletContext servletContext = servletContextEvent.getServletContext();
        PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
        String captchaSaver = servletContext.getInitParameter("captcha");
        String typeLocaleStorage = servletContext.getInitParameter("typeLocaleStorage");
        factoryCaptcha.setCaptcha(captchaSaver);
        factoryEpamLocaleStorage.setEpamStorageLocale(typeLocaleStorage);
        String cookieMaxAge = servletContext.getInitParameter(COOKIE_MAX_AGE);
        if (typeLocaleStorage.equals("cookie")) {
            ((CookieEpamStorageLocale) factoryEpamLocaleStorage.getEpamStorageLocale()).setMaxAge(Integer.parseInt(cookieMaxAge));
        }
        servletContext.setAttribute(COOKIE_MAX_AGE, cookieMaxAge);
        servletContext.setAttribute(TYPE_LOCALE_STORAGE, factoryEpamLocaleStorage.getEpamStorageLocale());
        servletContext.setAttribute(SAVER_CAPTCHA, factoryCaptcha.getCaptcha());
        servletContext.setAttribute(MAP_CAPTCHA, mapCaptcha);
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(SAVER_CAPTCHA);
        servletContext.removeAttribute(MAP_CAPTCHA);
        mapCaptcha.interrupt();
    }
}
